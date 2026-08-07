package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.IUserEntitlementRepository;
import com.nexerp.modules.admin.sql.UserEntitlementSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserEntitlementRepository implements IUserEntitlementRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getUserEntitlements(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", userId);
        return jdbc.queryList(UserEntitlementSql.GET_USER_ENTITLEMENTS, params);
    }

    @Override
    public void saveUserEntitlement(Map<String, Object> params) {
        jdbc.execute(UserEntitlementSql.SAVE_USER_ENTITLEMENT, params);
    }

    @Override
    public void deleteUserEntitlementsByUser(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", userId);
        jdbc.execute(UserEntitlementSql.DELETE_USER_ENTITLEMENTS_BY_USER, params);
    }
}
