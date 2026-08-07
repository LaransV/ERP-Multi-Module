package com.nexerp.modules.hr.controller;

import com.nexerp.common.*;
import com.nexerp.modules.hr.dto.response.HRMSDashboardResponse;
import com.nexerp.modules.hr.service.interfaces.IHRMSDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class HRMSDashboardController {

    private final IHRMSDashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<HRMSDashboardResponse>> dashboard() {
        log.info("dashboard START");
        try {
            HRMSDashboardResponse result = dashboardService.getDashboard();
            log.info("dashboard END");
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("dashboard Exception occurred", e);
            throw e;
        }
    }
}
