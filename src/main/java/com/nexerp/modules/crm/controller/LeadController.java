package com.nexerp.modules.crm.controller;

import com.nexerp.common.*;
import com.nexerp.modules.crm.dto.request.LeadRequest;
import com.nexerp.modules.crm.dto.request.LeadStatusRequest;
import com.nexerp.modules.crm.dto.response.LeadListItem;
import com.nexerp.modules.crm.dto.response.LeadResponse;
import com.nexerp.modules.crm.service.interfaces.ILeadService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/crm")
@RequiredArgsConstructor
public class LeadController {

    private final ILeadService leadService;

    @GetMapping("/leads")
    public ResponseEntity<ApiResponse<PagedResponse<LeadListItem>>> listLeads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        log.info("listLeads START | page={}, size={}, status={}, search={}", page, size, status, search);
        try {
            PagedResponse<LeadListItem> result = leadService.listLeads(page, size, status, search);
            log.info("listLeads END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listLeads Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/leads/{id}")
    public ResponseEntity<ApiResponse<LeadResponse>> getLead(@PathVariable Integer id) {
        log.info("getLead START | leadId={}", id);
        try {
            LeadResponse result = leadService.getLead(id);
            log.info("getLead END | leadId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getLead Exception occurred | leadId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/leads")
    public ResponseEntity<ApiResponse<LeadResponse>> createLead(
            @Valid @RequestBody LeadRequest req) {
        log.info("createLead START | leadName={}", req.getLeadName());
        try {
            LeadResponse result = leadService.createLead(req);
            log.info("createLead END | newLeadId={}", result.getLeadId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Lead created"));
        } catch (Exception e) {
            log.error("createLead Exception occurred | leadName={}", req.getLeadName(), e);
            throw e;
        }
    }

    @PutMapping("/leads/{id}")
    public ResponseEntity<ApiResponse<LeadResponse>> updateLead(
            @PathVariable Integer id, @Valid @RequestBody LeadRequest req) {
        log.info("updateLead START | leadId={}", id);
        try {
            LeadResponse result = leadService.updateLead(id, req);
            log.info("updateLead END | leadId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Lead updated"));
        } catch (Exception e) {
            log.error("updateLead Exception occurred | leadId={}", id, e);
            throw e;
        }
    }

    @PatchMapping("/leads/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateLeadStatus(
            @PathVariable Integer id, @RequestBody LeadStatusRequest req) {
        log.info("updateLeadStatus START | leadId={}, newStatus={}", id, req.getStatus());
        try {
            leadService.updateLeadStatus(id, req.getStatus());
            log.info("updateLeadStatus END | leadId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Status updated"));
        } catch (Exception e) {
            log.error("updateLeadStatus Exception occurred | leadId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/leads/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLead(@PathVariable Integer id) {
        log.info("deleteLead START | leadId={}", id);
        try {
            leadService.deleteLead(id);
            log.info("deleteLead END | leadId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Lead deleted"));
        } catch (Exception e) {
            log.error("deleteLead Exception occurred | leadId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/leads/{id}/convert")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> convertLead(
            @PathVariable Integer id) {
        log.info("convertLead START | leadId={}", id);
        try {
            Map<String, Integer> result = leadService.convertLead(id);
            log.info("convertLead END | leadId={}, clientId={}", id, result.get("clientId"));
            return ResponseEntity.ok(ApiResponse.ok(result, "Lead converted to client"));
        } catch (Exception e) {
            log.error("convertLead Exception occurred | leadId={}", id, e);
            throw e;
        }
    }
}
