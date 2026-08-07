package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.response.ModuleResponse;
import com.nexerp.modules.admin.service.interfaces.IModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ModuleController {

    private final IModuleService moduleService;

    @GetMapping("/modules")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> listModules() {
        log.info("listModules START");
        try {
            List<ModuleResponse> result = moduleService.listModules();
            log.info("listModules END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listModules Exception occurred", e);
            throw e;
        }
    }
}
