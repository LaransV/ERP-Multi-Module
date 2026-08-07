package com.nexerp.modules.crm.controller;

import com.nexerp.common.*;
import com.nexerp.modules.crm.dto.request.ActivityRequest;
import com.nexerp.modules.crm.dto.response.ActivityResponse;
import com.nexerp.modules.crm.service.interfaces.IActivityService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/crm")
@RequiredArgsConstructor
public class ActivityController {

    private final IActivityService activityService;

    @GetMapping("/activities")
    public ResponseEntity<ApiResponse<PagedResponse<ActivityResponse>>> listActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false) Integer leadId) {
        log.info("listActivities START | page={}, size={}, leadId={}", page, size, leadId);
        try {
            PagedResponse<ActivityResponse> result = activityService.listActivities(page, size, leadId);
            log.info("listActivities END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listActivities Exception occurred | leadId={}", leadId, e);
            throw e;
        }
    }

    @PostMapping("/activities")
    public ResponseEntity<ApiResponse<ActivityResponse>> createActivity(
            @Valid @RequestBody ActivityRequest req) {
        log.info("createActivity START | leadId={}", req.getLeadId());
        try {
            ActivityResponse result = activityService.createActivity(req);
            log.info("createActivity END | newActivityId={}", result.getActivityId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Activity created"));
        } catch (Exception e) {
            log.error("createActivity Exception occurred | leadId={}", req.getLeadId(), e);
            throw e;
        }
    }

    @PutMapping("/activities/{id}")
    public ResponseEntity<ApiResponse<ActivityResponse>> updateActivity(
            @PathVariable Integer id, @Valid @RequestBody ActivityRequest req) {
        log.info("updateActivity START | activityId={}", id);
        try {
            ActivityResponse result = activityService.updateActivity(id, req);
            log.info("updateActivity END | activityId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Activity updated"));
        } catch (Exception e) {
            log.error("updateActivity Exception occurred | activityId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/activities/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(@PathVariable Integer id) {
        log.info("deleteActivity START | activityId={}", id);
        try {
            activityService.deleteActivity(id);
            log.info("deleteActivity END | activityId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Activity deleted"));
        } catch (Exception e) {
            log.error("deleteActivity Exception occurred | activityId={}", id, e);
            throw e;
        }
    }
}
