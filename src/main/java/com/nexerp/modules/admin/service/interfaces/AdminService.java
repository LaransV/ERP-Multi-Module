package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.admin.dto.request.CompanyRequest;
import com.nexerp.modules.admin.dto.request.EntitlementRequest;
import com.nexerp.modules.admin.dto.request.RoleRequest;
import com.nexerp.modules.admin.dto.request.UserEntitlementSaveRequest;
import com.nexerp.modules.admin.dto.request.UserRequest;
import com.nexerp.modules.admin.dto.response.CompanyResponse;
import com.nexerp.modules.admin.dto.response.EntitlementResponse;
import com.nexerp.modules.admin.dto.response.ModuleResponse;
import com.nexerp.modules.admin.dto.response.RoleResponse;
import com.nexerp.modules.admin.dto.response.ScreenResponse;
import com.nexerp.modules.admin.dto.response.UserEntitlementRow;
import com.nexerp.modules.admin.dto.response.UserResponse;

import java.util.List;

public interface AdminService {

    PagedResponse<UserResponse> listUsers(int page, int size, String search);
    UserResponse getUser(Integer id);
    UserResponse createUser(UserRequest req);
    UserResponse updateUser(Integer id, UserRequest req);
    void toggleUser(Integer id);
    void deleteUser(Integer id);

    List<RoleResponse> listRoles();
    RoleResponse getRole(Integer id);
    RoleResponse createRole(RoleRequest req);
    RoleResponse updateRole(Integer id, RoleRequest req);
    void deleteRole(Integer id);

    List<EntitlementResponse> getEntitlements(Integer roleId);
    void saveEntitlements(Integer roleId, List<EntitlementRequest> entitlements);

    List<UserEntitlementRow> getUserEntitlements(Integer userId);
    void saveUserEntitlements(Integer userId, UserEntitlementSaveRequest req);
    void resetUserEntitlements(Integer userId);

    List<CompanyResponse> listCompanies();
    CompanyResponse getCompany(Integer id);
    CompanyResponse createCompany(CompanyRequest req);
    CompanyResponse updateCompany(Integer id, CompanyRequest req);
    void deleteCompany(Integer id);

    List<ModuleResponse> listModules();
    List<ScreenResponse> listScreens(Integer moduleId);
}
