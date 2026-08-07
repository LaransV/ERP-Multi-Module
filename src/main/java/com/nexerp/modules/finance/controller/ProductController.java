package com.nexerp.modules.finance.controller;

import com.nexerp.common.ApiResponse;
import com.nexerp.common.PagedResponse;
import com.nexerp.modules.finance.dto.request.*;
import com.nexerp.modules.finance.dto.response.*;
import com.nexerp.modules.finance.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ── Products ──────────────────────────────────────────────
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponseDto>>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        log.info("listProducts START | page={}, size={}, search={}", page, size, search);
        try {
            PagedResponse<ProductResponseDto> result = productService.listProducts(page, size, search);
            log.info("listProducts END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listProducts Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> searchProducts(
            @RequestParam String q) {
        log.info("searchProducts START | q={}", q);
        try {
            List<ProductResponseDto> result = productService.searchProducts(q);
            log.info("searchProducts END | found={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("searchProducts Exception occurred | q={}", q, e);
            throw e;
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(
            @PathVariable Integer id) {
        log.info("getProduct START | productId={}", id);
        try {
            ProductResponseDto result = productService.getProduct(id);
            log.info("getProduct END | productId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getProduct Exception occurred | productId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto req) {
        log.info("createProduct START | productName={}", req.getProductName());
        try {
            ProductResponseDto result = productService.createProduct(req);
            log.info("createProduct END | newProductId={}", result.getProductId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Product created"));
        } catch (Exception e) {
            log.error("createProduct Exception occurred | productName={}", req.getProductName(), e);
            throw e;
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Integer id, @Valid @RequestBody ProductRequestDto req) {
        log.info("updateProduct START | productId={}", id);
        try {
            ProductResponseDto result = productService.updateProduct(id, req);
            log.info("updateProduct END | productId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Product updated"));
        } catch (Exception e) {
            log.error("updateProduct Exception occurred | productId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        log.info("deleteProduct START | productId={}", id);
        try {
            productService.deleteProduct(id);
            log.info("deleteProduct END | productId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Product deleted"));
        } catch (Exception e) {
            log.error("deleteProduct Exception occurred | productId={}", id, e);
            throw e;
        }
    }

}


