package com.nexerp.modules.finance.controller;

import com.nexerp.common.ApiResponse;

import com.nexerp.modules.finance.dto.response.FinanceDashboardResponseDto;
import com.nexerp.modules.finance.service.interfaces.IFinanceDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceDashboardController {

    private final IFinanceDashboardService IDashboardService;
    // ── Dashboard ─────────────────────────────────────────────
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<FinanceDashboardResponseDto>> dashboard() {
        log.info("dashboard START");
        try {
            FinanceDashboardResponseDto result = IDashboardService.getDashboard();
            log.info("dashboard END");
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("dashboard Exception occurred", e);
            throw e;
        }
    }
}
