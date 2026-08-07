package com.nexerp.modules.hr.controller;

import com.nexerp.common.*;
import com.nexerp.modules.hr.dto.response.DesigResponse;
import com.nexerp.modules.hr.service.interfaces.IDesigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class DesigController {

    private final IDesigService desigService;

    @GetMapping("/designations")
    public ResponseEntity<ApiResponse<List<DesigResponse>>> listDesigs() {
        log.info("listDesigs START");
        try {
            List<DesigResponse> result = desigService.listDesigs();
            log.info("listDesigs END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listDesigs Exception occurred", e);
            throw e;
        }
    }
}
