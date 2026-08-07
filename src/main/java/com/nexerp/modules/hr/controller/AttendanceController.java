package com.nexerp.modules.hr.controller;

import com.nexerp.common.*;
import com.nexerp.modules.hr.dto.request.AttendanceRequest;
import com.nexerp.modules.hr.dto.response.AttendanceResponse;
import com.nexerp.modules.hr.service.interfaces.IAttendanceService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class AttendanceController {

    private final IAttendanceService attendanceService;

    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse<PagedResponse<AttendanceResponse>>> listAttendance(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        log.info("listAttendance START | page={}, size={}, empId={}, from={}, to={}", page, size, empId, fromDate, toDate);
        try {
            PagedResponse<AttendanceResponse> result = attendanceService.listAttendance(page, size, empId, fromDate, toDate);
            log.info("listAttendance END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listAttendance Exception occurred | empId={}", empId, e);
            throw e;
        }
    }

    @PostMapping("/attendance")
    public ResponseEntity<ApiResponse<AttendanceResponse>> createAttendance(
            @Valid @RequestBody AttendanceRequest req) {
        log.info("createAttendance START | empId={}, date={}", req.getEmpId(), req.getAttendanceDate());
        try {
            AttendanceResponse result = attendanceService.createAttendance(req);
            log.info("createAttendance END | newAttendanceId={}", result.getAttendanceId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Attendance recorded"));
        } catch (Exception e) {
            log.error("createAttendance Exception occurred | empId={}", req.getEmpId(), e);
            throw e;
        }
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> updateAttendance(
            @PathVariable Integer id, @Valid @RequestBody AttendanceRequest req) {
        log.info("updateAttendance START | attendanceId={}", id);
        try {
            AttendanceResponse result = attendanceService.updateAttendance(id, req);
            log.info("updateAttendance END | attendanceId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Attendance updated"));
        } catch (Exception e) {
            log.error("updateAttendance Exception occurred | attendanceId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/attendance/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(@PathVariable Integer id) {
        log.info("deleteAttendance START | attendanceId={}", id);
        try {
            attendanceService.deleteAttendance(id);
            log.info("deleteAttendance END | attendanceId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Attendance deleted"));
        } catch (Exception e) {
            log.error("deleteAttendance Exception occurred | attendanceId={}", id, e);
            throw e;
        }
    }
}
