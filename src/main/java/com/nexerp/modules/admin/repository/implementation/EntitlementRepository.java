package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.IEntitlementRepository;
import com.nexerp.modules.admin.sql.EntitlementSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EntitlementRepository implements IEntitlementRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getEntitlements(Integer roleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("RoleId", roleId);
        return jdbc.queryList(EntitlementSql.GET_ENTITLEMENTS, params);
    }

    @Override
    public void deleteEntitlementsByRole(Integer roleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("RoleId", roleId);
        jdbc.execute(EntitlementSql.DELETE_ENTITLEMENTS_BY_ROLE, params);
    }

    @Override
    public void insertEntitlement(Map<String, Object> params) {
        jdbc.execute(EntitlementSql.UPSERT_ENTITLEMENT, params);
    }
}
