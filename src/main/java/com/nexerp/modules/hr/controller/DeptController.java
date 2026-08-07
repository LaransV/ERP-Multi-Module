package com.nexerp.modules.hr.controller;

import com.nexerp.common.*;
import com.nexerp.modules.hr.dto.request.DeptRequest;
import com.nexerp.modules.hr.dto.response.DeptResponse;
import com.nexerp.modules.hr.service.interfaces.IDeptService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("/departments")
    public ResponseEntity<ApiResponse<List<DeptResponse>>> listDepts() {
        log.info("listDepts START");
        try {
            List<DeptResponse> result = deptService.listDepts();
            log.info("listDepts END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listDepts Exception occurred", e);
            throw e;
        }
    }

    @PostMapping("/departments")
    public ResponseEntity<ApiResponse<DeptResponse>> createDept(
            @Valid @RequestBody DeptRequest req) {
        log.info("createDept START | deptName={}", req.getDeptName());
        try {
            DeptResponse result = deptService.createDept(req);
            log.info("createDept END | newDeptId={}", result.getDeptId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Department created"));
        } catch (Exception e) {
            log.error("createDept Exception occurred | deptName={}", req.getDeptName(), e);
            throw e;
        }
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDept(@PathVariable Integer id) {
        log.info("deleteDept START | deptId={}", id);
        try {
            deptService.deleteDept(id);
            log.info("deleteDept END | deptId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Department deleted"));
        } catch (Exception e) {
            log.error("deleteDept Exception occurred | deptId={}", id, e);
            throw e;
        }
    }
}
