package com.nexerp.modules.inventory.controller;

import com.nexerp.common.*;
import com.nexerp.modules.inventory.dto.request.AdjustRequest;
import com.nexerp.modules.inventory.dto.response.StockItemResponse;
import com.nexerp.modules.inventory.service.interfaces.IStockService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class StockController {

    private final IStockService stockService;

    @GetMapping("/stock")
    public ResponseEntity<ApiResponse<PagedResponse<StockItemResponse>>> listStock(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String filter) {
        log.info("listStock START | page={}, size={}, filter={}", page, size, filter);
        try {
            PagedResponse<StockItemResponse> result = stockService.listStock(page, size, filter);
            log.info("listStock END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listStock Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<ApiResponse<StockItemResponse>> getStock(
            @PathVariable Integer id) {
        log.info("getStock START | stockId={}", id);
        try {
            StockItemResponse result = stockService.getStock(id);
            log.info("getStock END | stockId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getStock Exception occurred | stockId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/stock/adjust")
    public ResponseEntity<ApiResponse<Void>> adjust(
            @Valid @RequestBody AdjustRequest req) {
        log.info("adjustStock START | productId={}, qty={}, type={}", req.getProductId(), req.getQuantity(), req.getType());
        try {
            stockService.adjustStock(req);
            log.info("adjustStock END | productId={}", req.getProductId());
            return ResponseEntity.ok(ApiResponse.ok(null, "Stock adjusted"));
        } catch (Exception e) {
            log.error("adjustStock Exception occurred | productId={}", req.getProductId(), e);
            throw e;
        }
    }
}
