package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.modules.admin.dto.request.UserEntitlementSaveRequest;
import com.nexerp.modules.admin.dto.response.UserEntitlementRow;

import java.util.List;

public interface IUserEntitlementService {
    List<UserEntitlementRow> getUserEntitlements(Integer userId);
    void saveUserEntitlements(Integer userId, UserEntitlementSaveRequest req);
    void resetUserEntitlements(Integer userId);
}
