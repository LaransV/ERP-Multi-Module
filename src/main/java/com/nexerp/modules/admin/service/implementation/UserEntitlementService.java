package com.nexerp.modules.admin.service.implementation;

import com.nexerp.modules.admin.dto.request.UserEntitlementSaveRequest;
import com.nexerp.modules.admin.dto.response.UserEntitlementRow;
import com.nexerp.modules.admin.mapper.UserEntitlementMapper;
import com.nexerp.modules.admin.repository.interfaces.IUserEntitlementRepository;
import com.nexerp.modules.admin.service.interfaces.IUserEntitlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntitlementService implements IUserEntitlementService {

    private final IUserEntitlementRepository repository;
    private final UserEntitlementMapper      mapper;

    @Override
    public List<UserEntitlementRow> getUserEntitlements(Integer userId) {
        log.info("getUserEntitlements START | userId={}", userId);
        try {
            List<UserEntitlementRow> result =
                    repository.getUserEntitlements(userId)
                            .stream()
                            .map(mapper::toUserEntitlementRow)
                            .collect(Collectors.toList());
            log.info("getUserEntitlements END | userId={}, count={}", userId, result.size());
            return result;
        } catch (Exception e) {
            log.error("getUserEntitlements | Exception occurred | userId={}", userId, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserEntitlements(Integer userId, UserEntitlementSaveRequest req) {
        log.info("saveUserEntitlements START | userId={}, screens={}",
                userId, req.getScreens().size());
        try {
            for (UserEntitlementSaveRequest.ScreenPermission sp2 : req.getScreens()) {
                Map<String, Object> params = new HashMap<>();
                params.put("UserId",    userId);
                params.put("ScreenId",  sp2.getScreenId());
                params.put("CanCreate", sp2.isCanCreate());
                params.put("CanRead",   sp2.isCanRead());
                params.put("CanUpdate", sp2.isCanUpdate());
                params.put("CanDelete", sp2.isCanDelete());
                repository.saveUserEntitlement(params);
            }
            log.info("saveUserEntitlements END | userId={}", userId);
        } catch (Exception e) {
            log.error("saveUserEntitlements | Exception occurred | userId={}", userId, e);
            throw e;
        }
    }

    @Override
    public void resetUserEntitlements(Integer userId) {
        log.info("resetUserEntitlements START | userId={}", userId);
        try {
            repository.deleteUserEntitlementsByUser(userId);
            log.info("resetUserEntitlements END | userId={}", userId);
        } catch (Exception e) {
            log.error("resetUserEntitlements | Exception occurred | userId={}", userId, e);
            throw e;
        }
    }
}
