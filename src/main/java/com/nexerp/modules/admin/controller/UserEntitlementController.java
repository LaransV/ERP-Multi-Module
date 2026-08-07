package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.request.UserEntitlementSaveRequest;
import com.nexerp.modules.admin.dto.response.UserEntitlementRow;
import com.nexerp.modules.admin.service.interfaces.IUserEntitlementService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserEntitlementController {

    private final IUserEntitlementService userEntitlementService;

    /**
     * GET /api/admin/users/{id}/entitlements
     * Returns all screens with effective permissions for a user.
     * isUserOverride=true  → customised for this user
     * isUserOverride=false → showing role defaults
     */
    @GetMapping("/users/{id}/entitlements")
    public ResponseEntity<ApiResponse<List<UserEntitlementRow>>> getUserEntitlements(
            @PathVariable Integer id) {
        log.info("getUserEntitlements START | userId={}", id);
        try {
            List<UserEntitlementRow> result = userEntitlementService.getUserEntitlements(id);
            log.info("getUserEntitlements END | userId={}, count={}", id, result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getUserEntitlements Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    /**
     * POST /api/admin/users/{id}/entitlements
     * Upserts user-specific screen permissions.
     * Body: { "screens": [ { screenId, canCreate, canRead, canUpdate, canDelete } ] }
     */
    @PostMapping("/users/{id}/entitlements")
    public ResponseEntity<ApiResponse<Void>> saveUserEntitlements(
            @PathVariable Integer id,
            @RequestBody @Valid UserEntitlementSaveRequest req) {
        log.info("saveUserEntitlements START | userId={}, screens={}", id, req.getScreens().size());
        try {
            userEntitlementService.saveUserEntitlements(id, req);
            log.info("saveUserEntitlements END | userId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "User entitlements saved"));
        } catch (Exception e) {
            log.error("saveUserEntitlements Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    /**
     * DELETE /api/admin/users/{id}/entitlements
     * Resets user back to role-level permissions (removes all user-specific rows).
     */
    @DeleteMapping("/users/{id}/entitlements")
    public ResponseEntity<ApiResponse<Void>> resetUserEntitlements(
            @PathVariable Integer id) {
        log.info("resetUserEntitlements START | userId={}", id);
        try {
            userEntitlementService.resetUserEntitlements(id);
            log.info("resetUserEntitlements END | userId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "User entitlements reset to role defaults"));
        } catch (Exception e) {
            log.error("resetUserEntitlements Exception occurred | userId={}", id, e);
            throw e;
        }
    }
}
