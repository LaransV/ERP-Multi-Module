package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.request.EntitlementRequest;
import com.nexerp.modules.admin.dto.response.EntitlementResponse;
import com.nexerp.modules.admin.service.interfaces.IEntitlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class EntitlementController {

    private final IEntitlementService entitlementService;

    @GetMapping("/roles/{id}/entitlements")
    public ResponseEntity<ApiResponse<List<EntitlementResponse>>> getEntitlements(
            @PathVariable Integer id) {
        log.info("getEntitlements START | roleId={}", id);
        try {
            List<EntitlementResponse> result = entitlementService.getEntitlements(id);
            log.info("getEntitlements END | roleId={}, count={}", id, result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getEntitlements Exception occurred | roleId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/roles/{id}/entitlements")
    public ResponseEntity<ApiResponse<Void>> saveEntitlements(
            @PathVariable Integer id,
            @RequestBody List<EntitlementRequest> entitlements) {
        log.info("saveEntitlements START | roleId={}, entitlementCount={}", id, entitlements.size());
        try {
            entitlementService.saveEntitlements(id, entitlements);
            log.info("saveEntitlements END | roleId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Entitlements saved"));
        } catch (Exception e) {
            log.error("saveEntitlements Exception occurred | roleId={}", id, e);
            throw e;
        }
    }
}
