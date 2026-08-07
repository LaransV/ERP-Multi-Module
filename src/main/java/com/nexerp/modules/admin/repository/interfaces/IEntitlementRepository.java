package com.nexerp.modules.admin.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IEntitlementRepository {

    List<Map<String, Object>> getEntitlements(Integer roleId);
    void deleteEntitlementsByRole(Integer roleId);
    void insertEntitlement(Map<String, Object> params);
}
