package com.nexerp.modules.inventory.controller;

import com.nexerp.common.*;
import com.nexerp.modules.inventory.dto.response.MovementResponse;
import com.nexerp.modules.inventory.service.interfaces.IMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class MovementController {

    private final IMovementService movementService;

    @GetMapping("/movements")
    public ResponseEntity<ApiResponse<PagedResponse<MovementResponse>>> listMovements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) Integer productId) {
        log.info("listMovements START | page={}, size={}, productId={}", page, size, productId);
        try {
            PagedResponse<MovementResponse> result = movementService.listMovements(page, size, productId);
            log.info("listMovements END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listMovements Exception occurred | productId={}", productId, e);
            throw e;
        }
    }
}
