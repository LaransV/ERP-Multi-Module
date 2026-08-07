package com.nexerp.modules.crm.controller;

import com.nexerp.common.*;
import com.nexerp.modules.crm.dto.response.CRMDashboardResponse;
import com.nexerp.modules.crm.service.interfaces.ICRMDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/crm")
@RequiredArgsConstructor
public class CRMDashboardController {

    private final ICRMDashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<CRMDashboardResponse>> dashboard() {
        log.info("dashboard START");
        try {
            CRMDashboardResponse result = dashboardService.getDashboard();
            log.info("dashboard END");
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("dashboard Exception occurred", e);
            throw e;
        }
    }
}
