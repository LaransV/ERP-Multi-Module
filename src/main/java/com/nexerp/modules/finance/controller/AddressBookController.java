package com.nexerp.modules.finance.controller;

import com.nexerp.common.ApiResponse;
import com.nexerp.modules.finance.dto.request.DispatchAddressRequestDto;
import com.nexerp.modules.finance.dto.request.ShipToAddressRequestDto;
import com.nexerp.modules.finance.dto.response.DispatchAddressResponseDto;
import com.nexerp.modules.finance.dto.response.ShipToAddressResponseDto;
import com.nexerp.modules.finance.service.interfaces.IDispatchAddressService;
import com.nexerp.modules.finance.service.interfaces.IShipToAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Reusable "address book" entries used by the Invoice form's
 * Dispatch From and Ship To tabs (dropdown + quick-add).
 */
@Slf4j
@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class AddressBookController {

    private final IDispatchAddressService dispatchAddressService;
    private final IShipToAddressService   shipToAddressService;

    // ── Dispatch From addresses ──────────────────────────────
    @GetMapping("/dispatch-addresses")
    public ResponseEntity<ApiResponse<List<DispatchAddressResponseDto>>> listDispatchAddresses() {
        return ResponseEntity.ok(ApiResponse.ok(dispatchAddressService.listDispatchAddresses()));
    }

    @PostMapping("/dispatch-addresses")
    public ResponseEntity<ApiResponse<DispatchAddressResponseDto>> createDispatchAddress(
            @Valid @RequestBody DispatchAddressRequestDto req) {
        DispatchAddressResponseDto result = dispatchAddressService.createDispatchAddress(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result, "Dispatch address saved"));
    }

    // ── Ship To addresses ─────────────────────────────────────
    @GetMapping("/ship-to-addresses")
    public ResponseEntity<ApiResponse<List<ShipToAddressResponseDto>>> listShipToAddresses() {
        return ResponseEntity.ok(ApiResponse.ok(shipToAddressService.listShipToAddresses()));
    }

    @PostMapping("/ship-to-addresses")
    public ResponseEntity<ApiResponse<ShipToAddressResponseDto>> createShipToAddress(
            @Valid @RequestBody ShipToAddressRequestDto req) {
        ShipToAddressResponseDto result = shipToAddressService.createShipToAddress(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(result, "Ship-to address saved"));
    }
}
