package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.IUserRepository;
import com.nexerp.modules.admin.sql.UserSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getUsers(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(UserSql.GET_USERS, params);
    }

    @Override
    public Map<String, Object> getUserById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", id);
        return jdbc.queryOne(UserSql.GET_USER_BY_ID, params);
    }

    @Override
    public Integer insertUser(Map<String, Object> params) {
        return jdbc.executeAndGetId(UserSql.INSERT_USER, params);
    }

    @Override
    public void updateUser(Map<String, Object> params) {
        jdbc.execute(UserSql.UPDATE_USER, params);
    }

    @Override
    public void toggleUser(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", id);
        jdbc.execute(UserSql.TOGGLE_USER, params);
    }

    @Override
    public void deleteUser(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserId", id);
        jdbc.execute(UserSql.DELETE_USER, params);
    }
}
