package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.response.ScreenResponse;
import com.nexerp.modules.admin.service.interfaces.IScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ScreenController {

    private final IScreenService screenService;

    @GetMapping("/screens")
    public ResponseEntity<ApiResponse<List<ScreenResponse>>> listScreens(
            @RequestParam(required = false) Integer moduleId) {
        log.info("listScreens START | moduleId={}", moduleId);
        try {
            List<ScreenResponse> result = screenService.listScreens(moduleId);
            log.info("listScreens END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listScreens Exception occurred | moduleId={}", moduleId, e);
            throw e;
        }
    }
}
