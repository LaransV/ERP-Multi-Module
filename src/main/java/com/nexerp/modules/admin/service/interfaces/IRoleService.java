package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.modules.admin.dto.request.RoleRequest;
import com.nexerp.modules.admin.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    List<RoleResponse> listRoles();
    RoleResponse getRole(Integer id);
    RoleResponse createRole(RoleRequest req);
    RoleResponse updateRole(Integer id, RoleRequest req);
    void deleteRole(Integer id);
}
