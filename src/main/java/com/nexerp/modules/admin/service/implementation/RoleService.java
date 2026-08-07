package com.nexerp.modules.admin.service.implementation;

import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.admin.dto.request.RoleRequest;
import com.nexerp.modules.admin.dto.response.RoleResponse;
import com.nexerp.modules.admin.mapper.RoleMapper;
import com.nexerp.modules.admin.repository.interfaces.IRoleRepository;
import com.nexerp.modules.admin.service.interfaces.IRoleService;
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
public class RoleService implements IRoleService {

    private final IRoleRepository repository;
    private final RoleMapper      mapper;

    @Override
    public List<RoleResponse> listRoles() {
        log.info("listRoles START");
        try {
            List<RoleResponse> result = repository.getRoles()
                .stream().map(mapper::toRole).collect(Collectors.toList());
            log.info("listRoles END | count={}", result.size());
            return result;
        } catch (Exception e) {
            log.error("listRoles | Exception occurred", e);
            throw e;
        }
    }

    @Override
    public RoleResponse getRole(Integer id) {
        log.info("getRole START | roleId={}", id);
        try {
            Map<String, Object> row = repository.getRoleById(id);
            if (row == null) throw new ResourceNotFoundException("Role", id);
            RoleResponse result = mapper.toRole(row);
            log.info("getRole END | roleId={}", id);
            return result;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("getRole | Exception occurred | roleId={}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleResponse createRole(RoleRequest req) {
        log.info("createRole START | roleName={}", req.getRoleName());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("RoleName",        req.getRoleName());
            params.put("RoleDescription", req.getRoleDescription() != null ? req.getRoleDescription() : "");
            params.put("IsActive",        req.getIsActive());
            Integer newId = repository.insertRole(params);
            RoleResponse result = getRole(newId);
            log.info("createRole END | newRoleId={}", newId);
            return result;
        } catch (Exception e) {
            log.error("createRole | Exception occurred", e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleResponse updateRole(Integer id, RoleRequest req) {
        log.info("updateRole START | roleId={}", id);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("RoleId",          id);
            params.put("RoleName",        req.getRoleName());
            params.put("RoleDescription", req.getRoleDescription() != null ? req.getRoleDescription() : "");
            params.put("IsActive",        req.getIsActive());
            repository.updateRole(params);
            RoleResponse result = getRole(id);
            log.info("updateRole END | roleId={}", id);
            return result;
        } catch (Exception e) {
            log.error("updateRole | Exception occurred | roleId={}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Integer id) {
        log.info("deleteRole START | roleId={}", id);
        try {
            repository.deleteRole(id);
            log.info("deleteRole END | roleId={}", id);
        } catch (Exception e) {
            log.error("deleteRole | Exception occurred | roleId={}", id, e);
            throw e;
        }
    }
}
