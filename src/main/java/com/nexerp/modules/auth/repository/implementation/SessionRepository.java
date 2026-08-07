package com.nexerp.modules.auth.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.auth.repository.interfaces.ISessionRepository;
import com.nexerp.modules.auth.sql.AuthSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SessionRepository implements ISessionRepository {

    private final JdbcExecutor jdbc;

    @Override
    public Map<String, Object> getUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("Username", username);
        return jdbc.queryOne(AuthSql.GET_USER_BY_USERNAME, params);
    }

    /** Same user-level/role-level branching as ProfileRepository - see there
     * for why this needs two queries instead of one. */
    @Override
    public List<Map<String, Object>> getUserPermissions(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", userId);
        boolean hasUserLevel = !jdbc.queryList(AuthSql.HAS_USER_ENTITLEMENTS, params).isEmpty();
        return hasUserLevel
                ? jdbc.queryList(AuthSql.GET_PERMISSIONS_USER_LEVEL, params)
                : jdbc.queryList(AuthSql.GET_PERMISSIONS_ROLE_LEVEL, params);
    }

    @Override
    public void updateLastLogin(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", userId);
        jdbc.execute(AuthSql.UPDATE_LAST_LOGIN, params);
    }
}
