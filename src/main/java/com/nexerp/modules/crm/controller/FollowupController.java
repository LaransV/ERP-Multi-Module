package com.nexerp.modules.crm.controller;

import com.nexerp.common.*;
import com.nexerp.modules.crm.dto.request.CompleteFollowupRequest;
import com.nexerp.modules.crm.dto.request.FollowupRequest;
import com.nexerp.modules.crm.dto.response.FollowupResponse;
import com.nexerp.modules.crm.service.interfaces.IFollowupService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/crm")
@RequiredArgsConstructor
public class FollowupController {

    private final IFollowupService followupService;

    @GetMapping("/followups")
    public ResponseEntity<ApiResponse<PagedResponse<FollowupResponse>>> listFollowups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false) Integer leadId,
            @RequestParam(required = false) String status) {
        log.info("listFollowups START | page={}, size={}, leadId={}, status={}", page, size, leadId, status);
        try {
            PagedResponse<FollowupResponse> result = followupService.listFollowups(page, size, leadId, status);
            log.info("listFollowups END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listFollowups Exception occurred | leadId={}", leadId, e);
            throw e;
        }
    }

    @PostMapping("/followups")
    public ResponseEntity<ApiResponse<FollowupResponse>> createFollowup(
            @Valid @RequestBody FollowupRequest req) {
        log.info("createFollowup START | leadId={}", req.getLeadId());
        try {
            FollowupResponse result = followupService.createFollowup(req);
            log.info("createFollowup END | newFollowupId={}", result.getFollowupId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Followup scheduled"));
        } catch (Exception e) {
            log.error("createFollowup Exception occurred | leadId={}", req.getLeadId(), e);
            throw e;
        }
    }

    @PutMapping("/followups/{id}")
    public ResponseEntity<ApiResponse<FollowupResponse>> updateFollowup(
            @PathVariable Integer id, @Valid @RequestBody FollowupRequest req) {
        log.info("updateFollowup START | followupId={}", id);
        try {
            FollowupResponse result = followupService.updateFollowup(id, req);
            log.info("updateFollowup END | followupId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Followup updated"));
        } catch (Exception e) {
            log.error("updateFollowup Exception occurred | followupId={}", id, e);
            throw e;
        }
    }

    @PatchMapping("/followups/{id}/complete")
    public ResponseEntity<ApiResponse<Void>> completeFollowup(
            @PathVariable Integer id, @RequestBody CompleteFollowupRequest req) {
        log.info("completeFollowup START | followupId={}", id);
        try {
            followupService.completeFollowup(id, req.getNotes());
            log.info("completeFollowup END | followupId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Followup completed"));
        } catch (Exception e) {
            log.error("completeFollowup Exception occurred | followupId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/followups/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFollowup(@PathVariable Integer id) {
        log.info("deleteFollowup START | followupId={}", id);
        try {
            followupService.deleteFollowup(id);
            log.info("deleteFollowup END | followupId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Followup deleted"));
        } catch (Exception e) {
            log.error("deleteFollowup Exception occurred | followupId={}", id, e);
            throw e;
        }
    }
}
