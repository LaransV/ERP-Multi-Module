package com.nexerp.modules.inventory.controller;

import com.nexerp.common.*;
import com.nexerp.modules.inventory.dto.response.InventoryDashboardResponse;
import com.nexerp.modules.inventory.service.interfaces.IInventoryDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryDashboardController {

    private final IInventoryDashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<InventoryDashboardResponse>> dashboard() {
        log.info("dashboard START");
        try {
            InventoryDashboardResponse result = dashboardService.getDashboard();
            log.info("dashboard END");
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("dashboard Exception occurred", e);
            throw e;
        }
    }
}
