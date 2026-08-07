package com.nexerp.modules.auth.service.implementation;

import com.nexerp.common.BusinessException;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.dto.request.ChangePasswordRequest;
import com.nexerp.modules.auth.dto.response.PermissionDto;
import com.nexerp.modules.auth.dto.response.UserInfo;
import com.nexerp.modules.auth.mapper.ProfileMapper;
import com.nexerp.modules.auth.repository.interfaces.IProfileRepository;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.auth.service.interfaces.IProfileService;
import com.nexerp.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final IProfileRepository repository;
    private final ProfileMapper      mapper;
    private final PasswordEncoder    encoder;
    private final AuthService        auth;

    @Override
    public UserInfo me() {
        log.info("me START");
        try {
            UserPrincipal p = auth.currentPrincipal();
            Map<String, Object> row = repository.getUserByUsername(p.getUsername());
            if (row == null) throw new ResourceNotFoundException("User", p.getUserId());
            List<PermissionDto> perms = mapper.toPermissions(repository.getUserPermissions(p.getUserId()));
            UserInfo result = mapper.toUserInfo(p, perms);
            log.info("me END | userId={}", p.getUserId());
            return result;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("me | Exception occurred", e);
            throw e;
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest req) {
        log.info("changePassword START");
        try {
            UserPrincipal p = auth.currentPrincipal();
            Map<String, Object> row = repository.getUserByUsername(p.getUsername());
            if (row == null) throw new ResourceNotFoundException("User", p.getUserId());
            if (!encoder.matches(req.getCurrentPassword(), (String) row.get("password_hash")))
                throw new BusinessException("Current password is incorrect");
            repository.changePassword(p.getUserId(), encoder.encode(req.getNewPassword()));
            log.info("changePassword END | userId={}", p.getUserId());
        } catch (BusinessException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("changePassword | Exception occurred", e);
            throw e;
        }
    }
}
