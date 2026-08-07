package com.nexerp.modules.admin.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IUserRepository {

    List<Map<String, Object>> getUsers(Map<String, Object> params);
    Map<String, Object> getUserById(Integer id);
    Integer insertUser(Map<String, Object> params);
    void updateUser(Map<String, Object> params);
    void toggleUser(Integer id);
    void deleteUser(Integer id);
}
