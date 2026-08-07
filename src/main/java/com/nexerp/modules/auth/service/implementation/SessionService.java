package com.nexerp.modules.auth.service.implementation;

import com.nexerp.common.BusinessException;
import com.nexerp.modules.auth.dto.request.LoginRequest;
import com.nexerp.modules.auth.dto.response.LoginResponse;
import com.nexerp.modules.auth.dto.response.PermissionDto;
import com.nexerp.modules.auth.dto.response.UserInfo;
import com.nexerp.modules.auth.mapper.SessionMapper;
import com.nexerp.modules.auth.repository.interfaces.ISessionRepository;
import com.nexerp.modules.auth.service.interfaces.ISessionService;
import com.nexerp.security.JwtTokenProvider;
import com.nexerp.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final ISessionRepository repository;
    private final SessionMapper      mapper;
    private final JwtTokenProvider   jwt;
    private final PasswordEncoder    encoder;

    @Override
    public LoginResponse login(LoginRequest req) {
        log.info("login START | username={}", req.getUsername());
        try {
            Map<String, Object> userRow = repository.getUserByUsername(req.getUsername());

            if (userRow == null) {
                log.warn("login | User not found | username={}", req.getUsername());
                throw new BadCredentialsException("Invalid credentials");
            }

            String storedHash = (String) userRow.get("password_hash");
            boolean isActive  = Boolean.TRUE.equals(userRow.get("is_active"));

            if (!isActive) {
                log.warn("login | Account deactivated | username={}", req.getUsername());
                throw new BusinessException("Account is deactivated. Contact admin.");
            }
            if (!encoder.matches(req.getPassword(), storedHash)) {
                log.warn("login | Invalid password | username={}", req.getUsername());
                throw new BadCredentialsException("Invalid credentials");
            }

            Integer userId = ((Number) userRow.get("user_id")).intValue();

            Integer companyId = userRow.get("company_id") != null
                    ? ((Number) userRow.get("company_id")).intValue() : null;

            log.info("login | companyId={}", companyId);

            UserPrincipal principal = UserPrincipal.builder()
                .userId(userId)
                .username((String) userRow.get("username"))
                .fullName((String) userRow.get("full_name"))
                .email((String) userRow.get("email"))
                .roleId(((Number) userRow.get("role_id")).intValue())
                .roleName((String) userRow.get("role_name"))
                .active(isActive)
                .companyId(companyId)
                .build();

            List<PermissionDto> permissions = mapper.toPermissions(repository.getUserPermissions(userId));

            repository.updateLastLogin(userId);

            String token = jwt.generate(principal);
            UserInfo info = mapper.toUserInfo(principal, permissions);

            LoginResponse response = new LoginResponse();
            response.setAccessToken(token);
            response.setUser(info);
            log.info("login END | userId={}", userId);
            return response;
        } catch (BadCredentialsException | BusinessException e) {
            log.warn("login | Auth rejected | username={} | {}", req.getUsername(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("login | Exception occurred | username={}", req.getUsername(), e);
            throw e;
        }
    }
}
