package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.IRoleRepository;
import com.nexerp.modules.admin.sql.RoleSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RoleRepository implements IRoleRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getRoles() {
        return jdbc.queryList(RoleSql.GET_ROLES, new HashMap<>());
    }

    @Override
    public Map<String, Object> getRoleById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("RoleId", id);
        return jdbc.queryOne(RoleSql.GET_ROLE_BY_ID, params);
    }

    @Override
    public Integer insertRole(Map<String, Object> params) {
        return jdbc.executeAndGetId(RoleSql.INSERT_ROLE, params);
    }

    @Override
    public void updateRole(Map<String, Object> params) {
        jdbc.execute(RoleSql.UPDATE_ROLE, params);
    }

    // Original proc deletes RoleEntitlements first, then the Role - both
    // together. Preserved here; safe because this only runs from a
    // @Transactional service method.
    @Override
    public void deleteRole(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("RoleId", id);
        jdbc.execute(RoleSql.DELETE_ROLE_ENTITLEMENTS, params);
        jdbc.execute(RoleSql.DELETE_ROLE, params);
    }
}
