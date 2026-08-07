package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.modules.admin.dto.request.EntitlementRequest;
import com.nexerp.modules.admin.dto.response.EntitlementResponse;

import java.util.List;

public interface IEntitlementService {
    List<EntitlementResponse> getEntitlements(Integer roleId);
    void saveEntitlements(Integer roleId, List<EntitlementRequest> entitlements);
}
