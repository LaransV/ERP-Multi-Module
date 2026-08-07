package com.nexerp.modules.admin.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IRoleRepository {

    List<Map<String, Object>> getRoles();
    Map<String, Object> getRoleById(Integer id);
    Integer insertRole(Map<String, Object> params);
    void updateRole(Map<String, Object> params);
    void deleteRole(Integer id);
}
