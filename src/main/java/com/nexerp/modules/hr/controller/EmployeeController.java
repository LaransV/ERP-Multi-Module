package com.nexerp.modules.hr.controller;

import com.nexerp.common.*;
import com.nexerp.modules.hr.dto.request.EmployeeRequest;
import com.nexerp.modules.hr.dto.response.EmployeeResponse;
import com.nexerp.modules.hr.service.interfaces.IEmployeeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<PagedResponse<EmployeeResponse>>> listEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        log.info("listEmployees START | page={}, size={}, status={}, search={}", page, size, status, search);
        try {
            PagedResponse<EmployeeResponse> result = employeeService.listEmployees(page, size, status, search);
            log.info("listEmployees END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listEmployees Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(
            @PathVariable Integer id) {
        log.info("getEmployee START | empId={}", id);
        try {
            EmployeeResponse result = employeeService.getEmployee(id);
            log.info("getEmployee END | empId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getEmployee Exception occurred | empId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest req) {
        log.info("createEmployee START | empName={} {}", req.getFirstName(), req.getLastName());
        try {
            EmployeeResponse result = employeeService.createEmployee(req);
            log.info("createEmployee END | newEmpId={}", result.getEmpId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Employee created"));
        } catch (Exception e) {
            log.error("createEmployee Exception occurred | empName={} {}", req.getFirstName(), req.getLastName(), e);
            throw e;
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Integer id, @Valid @RequestBody EmployeeRequest req) {
        log.info("updateEmployee START | empId={}", id);
        try {
            EmployeeResponse result = employeeService.updateEmployee(id, req);
            log.info("updateEmployee END | empId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Employee updated"));
        } catch (Exception e) {
            log.error("updateEmployee Exception occurred | empId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Integer id) {
        log.info("deleteEmployee START | empId={}", id);
        try {
            employeeService.deleteEmployee(id);
            log.info("deleteEmployee END | empId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Employee deleted"));
        } catch (Exception e) {
            log.error("deleteEmployee Exception occurred | empId={}", id, e);
            throw e;
        }
    }
}
