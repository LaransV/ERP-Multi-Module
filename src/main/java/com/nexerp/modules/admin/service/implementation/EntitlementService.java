package com.nexerp.modules.admin.service.implementation;

import com.nexerp.modules.admin.dto.request.EntitlementRequest;
import com.nexerp.modules.admin.dto.response.EntitlementResponse;
import com.nexerp.modules.admin.mapper.EntitlementMapper;
import com.nexerp.modules.admin.repository.interfaces.IEntitlementRepository;
import com.nexerp.modules.admin.service.interfaces.IEntitlementService;
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
public class EntitlementService implements IEntitlementService {

    private final IEntitlementRepository repository;
    private final EntitlementMapper      mapper;

    @Override
    public List<EntitlementResponse> getEntitlements(Integer roleId) {
        log.info("getEntitlements START | roleId={}", roleId);
        try {
            List<EntitlementResponse> result = repository.getEntitlements(roleId)
                .stream().map(mapper::toEntitlement).collect(Collectors.toList());
            log.info("getEntitlements END | roleId={}, count={}", roleId, result.size());
            return result;
        } catch (Exception e) {
            log.error("getEntitlements | Exception occurred | roleId={}", roleId, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEntitlements(Integer roleId, List<EntitlementRequest> entitlements) {
        log.info("saveEntitlements START | roleId={}, count={}", roleId, entitlements.size());
        try {
            repository.deleteEntitlementsByRole(roleId);
            for (EntitlementRequest e : entitlements) {
                Map<String, Object> params = new HashMap<>();
                params.put("RoleId",    roleId);
                params.put("ScreenId",  e.getScreenId());
                params.put("CanCreate", e.isCanCreate());
                params.put("CanRead",   e.isCanRead());
                params.put("CanUpdate", e.isCanUpdate());
                params.put("CanDelete", e.isCanDelete());
                repository.insertEntitlement(params);
            }
            log.info("saveEntitlements END | roleId={}", roleId);
        } catch (Exception e) {
            log.error("saveEntitlements | Exception occurred | roleId={}", roleId, e);
            throw e;
        }
    }
}
