package com.nexerp.modules.inventory.controller;

import com.nexerp.common.*;
import com.nexerp.modules.inventory.dto.request.PoRequest;
import com.nexerp.modules.inventory.dto.response.PoListItem;
import com.nexerp.modules.inventory.dto.response.PoResponse;
import com.nexerp.modules.inventory.service.interfaces.IPurchaseOrderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final IPurchaseOrderService purchaseOrderService;

    @GetMapping("/purchase-orders")
    public ResponseEntity<ApiResponse<PagedResponse<PoListItem>>> listPOs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        log.info("listPOs START | page={}, size={}, status={}", page, size, status);
        try {
            PagedResponse<PoListItem> result = purchaseOrderService.listPOs(page, size, status);
            log.info("listPOs END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listPOs Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/purchase-orders/{id}")
    public ResponseEntity<ApiResponse<PoResponse>> getPO(@PathVariable Integer id) {
        log.info("getPO START | poId={}", id);
        try {
            PoResponse result = purchaseOrderService.getPO(id);
            log.info("getPO END | poId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getPO Exception occurred | poId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/purchase-orders")
    public ResponseEntity<ApiResponse<PoResponse>> createPO(
            @Valid @RequestBody PoRequest req) {
        log.info("createPO START | supplierName={}", req.getSupplierName());
        try {
            PoResponse result = purchaseOrderService.createPO(req);
            log.info("createPO END | newPoId={}", result.getPoId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "PO created"));
        } catch (Exception e) {
            log.error("createPO Exception occurred | supplierName={}", req.getSupplierName(), e);
            throw e;
        }
    }

    @PatchMapping("/purchase-orders/{id}/approve")
    public ResponseEntity<ApiResponse<Void>> approvePO(@PathVariable Integer id) {
        log.info("approvePO START | poId={}", id);
        try {
            purchaseOrderService.approvePO(id);
            log.info("approvePO END | poId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "PO approved"));
        } catch (Exception e) {
            log.error("approvePO Exception occurred | poId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/purchase-orders/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePO(@PathVariable Integer id) {
        log.info("deletePO START | poId={}", id);
        try {
            purchaseOrderService.deletePO(id);
            log.info("deletePO END | poId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "PO deleted"));
        } catch (Exception e) {
            log.error("deletePO Exception occurred | poId={}", id, e);
            throw e;
        }
    }
}
