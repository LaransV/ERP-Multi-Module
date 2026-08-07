package com.nexerp.modules.auth.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.auth.repository.interfaces.IProfileRepository;
import com.nexerp.modules.auth.sql.AuthSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProfileRepository implements IProfileRepository {

    private final JdbcExecutor jdbc;

    @Override
    public Map<String, Object> getUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("Username", username);
        return jdbc.queryOne(AuthSql.GET_USER_BY_USERNAME, params);
    }

    /** Reproduces the original proc's IF EXISTS/ELSE branching:
     * user-level entitlements if any exist for this user, else role-level. */
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
    public void changePassword(Integer userId, String newPasswordHash) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", userId);
        params.put("PasswordHash", newPasswordHash);
        jdbc.execute(AuthSql.CHANGE_PASSWORD, params);
    }
}
