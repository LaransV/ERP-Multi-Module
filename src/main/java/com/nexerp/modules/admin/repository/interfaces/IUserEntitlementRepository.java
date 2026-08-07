package com.nexerp.modules.admin.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IUserEntitlementRepository {

    List<Map<String, Object>> getUserEntitlements(Integer userId);
    void saveUserEntitlement(Map<String, Object> params);
    void deleteUserEntitlementsByUser(Integer userId);
}
