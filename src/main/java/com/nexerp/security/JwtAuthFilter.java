package com.nexerp.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {
        String token = extractToken(req);
        if (StringUtils.hasText(token) && jwt.validate(token)) {
            try {
                Claims c = jwt.parse(token);

                // Frontend sends X-Company-Id when user switches company
                Integer companyId = c.get("companyId", Integer.class);
                String headerCompanyId = req.getHeader("X-Company-Id");
                if (StringUtils.hasText(headerCompanyId)) {
                    try {
                        companyId = Integer.parseInt(headerCompanyId);
                    } catch (NumberFormatException ex) {
                        log.warn("Invalid X-Company-Id header: {}", headerCompanyId);
                    }
                }

                UserPrincipal principal = UserPrincipal.builder()
                        .userId(Integer.parseInt(c.getSubject()))
                        .username(c.get("username", String.class))
                        .fullName(c.get("fullName",  String.class))
                        .email(c.get("email",        String.class))
                        .roleId(c.get("roleId",      Integer.class))
                        .roleName(c.get("roleName",  String.class))
                        .active(true)
                        .companyId(companyId)
                        .build();

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + principal.getRoleName()));
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(principal, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                log.warn("JWT authentication failed: {}", e.getMessage());
            }
        }
        chain.doFilter(req, res);
    }

    private String extractToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return (StringUtils.hasText(header) && header.startsWith("Bearer "))
                ? header.substring(7) : null;
    }
}
