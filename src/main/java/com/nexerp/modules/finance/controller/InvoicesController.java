package com.nexerp.modules.finance.controller;

import com.nexerp.common.ApiResponse;
import com.nexerp.common.PagedResponse;
import com.nexerp.modules.finance.service.interfaces.IInvoiceService;
import com.nexerp.modules.finance.dto.request.InvoiceRequestDto;
import com.nexerp.modules.finance.dto.response.GetAllInvoiceDto;
import com.nexerp.modules.finance.dto.response.InvoiceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class InvoicesController {

    private final IInvoiceService IInvoiceService;


    // ── Invoices ──────────────────────────────────────────────
    @GetMapping("/invoices")
    public ResponseEntity<ApiResponse<PagedResponse<GetAllInvoiceDto>>> listInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer clientId) {
        log.info("listInvoices START | page={}, size={}, status={}, clientId={}", page, size, status, clientId);
        try {
            PagedResponse<GetAllInvoiceDto> result = IInvoiceService.listInvoices(page, size, status, clientId);
            log.info("listInvoices END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listInvoices Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> getInvoice(
            @PathVariable Integer id) {
        log.info("getInvoice START | invoiceId={}", id);
        try {
            InvoiceResponseDto result = IInvoiceService.getInvoice(id);
            log.info("getInvoice END | invoiceId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getInvoice Exception occurred | invoiceId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/invoices")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> createInvoice(
            @Valid @RequestBody InvoiceRequestDto req) {
        log.info("createInvoice START | clientId={}, grandTotal={}", req.getClientId(), req.getGrandTotal());
        try {
            InvoiceResponseDto result = IInvoiceService.createInvoice(req);
            log.info("createInvoice END | newInvoiceId={}", result.getInvoiceId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Invoice created"));
        } catch (Exception e) {
            log.error("createInvoice Exception occurred | clientId={}", req.getClientId(), e);
            throw e;
        }
    }

    @PutMapping("/invoices/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponseDto>> updateInvoice(
            @PathVariable Integer id, @Valid @RequestBody InvoiceRequestDto req) {
        log.info("updateInvoice START | invoiceId={}", id);
        try {
            InvoiceResponseDto result = IInvoiceService.updateInvoice(id, req);
            log.info("updateInvoice END | invoiceId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Invoice updated"));
        } catch (Exception e) {
            log.error("updateInvoice Exception occurred | invoiceId={}", id, e);
            throw e;
        }
    }

    @PatchMapping("/invoices/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Integer id, @RequestBody InvoiceRequestDto.StatusUpdateRequest req) {
        log.info("updateInvoiceStatus START | invoiceId={}, newStatus={}", id, req.getStatus());
        try {
            IInvoiceService.updateInvoiceStatus(id, req.getStatus());
            log.info("updateInvoiceStatus END | invoiceId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Status updated"));
        } catch (Exception e) {
            log.error("updateInvoiceStatus Exception occurred | invoiceId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Integer id) {
        log.info("deleteInvoice START | invoiceId={}", id);
        try {
            IInvoiceService.deleteInvoice(id);
            log.info("deleteInvoice END | invoiceId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Invoice deleted"));
        } catch (Exception e) {
            log.error("deleteInvoice Exception occurred | invoiceId={}", id, e);
            throw e;
        }
    }
}
