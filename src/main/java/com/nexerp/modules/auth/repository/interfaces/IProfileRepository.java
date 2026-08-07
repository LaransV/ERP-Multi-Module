package com.nexerp.modules.auth.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IProfileRepository {

    Map<String, Object> getUserByUsername(String username);
    List<Map<String, Object>> getUserPermissions(Integer userId);
    void changePassword(Integer userId, String newPasswordHash);
}
