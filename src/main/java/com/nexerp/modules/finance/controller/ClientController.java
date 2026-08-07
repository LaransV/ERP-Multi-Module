package com.nexerp.modules.finance.controller;

import com.nexerp.common.*;
import com.nexerp.common.export.ExportHttpUtil;
import com.nexerp.modules.finance.dto.request.ClientRequestDto;
import com.nexerp.modules.finance.dto.response.ClientResponseDto;

import javax.validation.Valid;

import com.nexerp.modules.finance.service.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService IClientService;


    // ── Clients ───────────────────────────────────────────────
    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<PagedResponse<ClientResponseDto>>> listClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String stateName) {
        log.info("listClients START | page={}, size={}, search={}, stateName={}", page, size, search, stateName);
        try {
            PagedResponse<ClientResponseDto> result = IClientService.listClients(page, size, search, stateName);
            log.info("listClients END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listClients Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/clients/export/excel")
    public ResponseEntity<byte[]> exportClientsExcel(
            @RequestParam(required = false) String stateName) {
        log.info("exportClientsExcel START | stateName={}", stateName);
        try {
            byte[] data = IClientService.exportClientsExcel(stateName);
            String filename = "clients" + (stateName != null && !stateName.isEmpty() ? "_" + stateName.replace(" ", "_") : "") + ".xlsx";
            log.info("exportClientsExcel END");
            return ExportHttpUtil.excelResponse(data, filename);
        } catch (Exception e) {
            log.error("exportClientsExcel Exception occurred", e);
            throw e;
        }
    }

    @GetMapping("/clients/export/pdf")
    public ResponseEntity<byte[]> exportClientsPdf(
            @RequestParam(required = false) String stateName) {
        log.info("exportClientsPdf START | stateName={}", stateName);
        try {
            byte[] data = IClientService.exportClientsPdf(stateName);
            String filename = "clients" + (stateName != null && !stateName.isEmpty() ? "_" + stateName.replace(" ", "_") : "") + ".pdf";
            log.info("exportClientsPdf END");
            return ExportHttpUtil.pdfResponse(data, filename);
        } catch (Exception e) {
            log.error("exportClientsPdf Exception occurred", e);
            throw e;
        }
    }

    @GetMapping("/clients/search")
    public ResponseEntity<ApiResponse<List<ClientResponseDto>>> searchClients(
            @RequestParam String q) {
        log.info("searchClients START | q={}", q);
        try {
            List<ClientResponseDto> result = IClientService.searchClients(q);
            log.info("searchClients END | found={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("searchClients Exception occurred | q={}", q, e);
            throw e;
        }
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> getClient(
            @PathVariable Integer id) {
        log.info("getClient START | clientId={}", id);
        try {
            ClientResponseDto result = IClientService.getClient(id);
            log.info("getClient END | clientId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getClient Exception occurred | clientId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<ApiResponse<ClientResponseDto>> createClient(
            @Valid @RequestBody ClientRequestDto req) {
        log.info("createClient START | clientName={}", req.getClientName());
        try {
            ClientResponseDto result = IClientService.createClient(req);
            log.info("createClient END | newClientId={}", result.getClientId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Client created"));
        } catch (Exception e) {
            log.error("createClient Exception occurred | clientName={}", req.getClientName(), e);
            throw e;
        }
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDto>> updateClient(
            @PathVariable Integer id, @Valid @RequestBody ClientRequestDto req) {
        log.info("updateClient START | clientId={}", id);
        try {
            ClientResponseDto result = IClientService.updateClient(id, req);
            log.info("updateClient END | clientId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Client updated"));
        } catch (Exception e) {
            log.error("updateClient Exception occurred | clientId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Integer id) {
        log.info("deleteClient START | clientId={}", id);
        try {
            IClientService.deleteClient(id);
            log.info("deleteClient END | clientId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Client deleted"));
        } catch (Exception e) {
            log.error("deleteClient Exception occurred | clientId={}", id, e);
            throw e;
        }
    }

}
