package com.nexerp.modules.hr.controller;

import com.nexerp.common.*;
import com.nexerp.modules.hr.dto.request.PayrollProcessRequest;
import com.nexerp.modules.hr.dto.response.PayrollResponse;
import com.nexerp.modules.hr.service.interfaces.IPayrollService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class PayrollController {

    private final IPayrollService payrollService;

    @GetMapping("/payroll")
    public ResponseEntity<ApiResponse<PagedResponse<PayrollResponse>>> listPayroll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        log.info("listPayroll START | page={}, size={}, month={}, year={}", page, size, month, year);
        try {
            PagedResponse<PayrollResponse> result = payrollService.listPayroll(page, size, month, year);
            log.info("listPayroll END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listPayroll Exception occurred | month={}, year={}", month, year, e);
            throw e;
        }
    }

    @GetMapping("/payroll/{id}")
    public ResponseEntity<ApiResponse<PayrollResponse>> getPayroll(@PathVariable Integer id) {
        log.info("getPayroll START | payrollId={}", id);
        try {
            PayrollResponse result = payrollService.getPayroll(id);
            log.info("getPayroll END | payrollId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getPayroll Exception occurred | payrollId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/payroll/process")
    public ResponseEntity<ApiResponse<Void>> processPayroll(
            @Valid @RequestBody PayrollProcessRequest req) {
        log.info("processPayroll START | month={}, year={}", req.getMonth(), req.getYear());
        try {
            payrollService.processPayroll(req.getMonth(), req.getYear());
            log.info("processPayroll END | month={}, year={}", req.getMonth(), req.getYear());
            return ResponseEntity.ok(ApiResponse.ok(null, "Payroll processing started"));
        } catch (Exception e) {
            log.error("processPayroll Exception occurred | month={}, year={}", req.getMonth(), req.getYear(), e);
            throw e;
        }
    }

    @PatchMapping("/payroll/{id}/paid")
    public ResponseEntity<ApiResponse<Void>> markPaid(@PathVariable Integer id) {
        log.info("markPayrollPaid START | payrollId={}", id);
        try {
            payrollService.markPayrollPaid(id);
            log.info("markPayrollPaid END | payrollId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Payroll marked as paid"));
        } catch (Exception e) {
            log.error("markPayrollPaid Exception occurred | payrollId={}", id, e);
            throw e;
        }
    }
}
