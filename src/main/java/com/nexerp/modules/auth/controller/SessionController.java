package com.nexerp.modules.auth.controller;

import com.nexerp.common.ApiResponse;
import com.nexerp.modules.auth.dto.request.LoginRequest;
import com.nexerp.modules.auth.dto.response.LoginResponse;
import com.nexerp.modules.auth.service.interfaces.ISessionService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SessionController {

    private final ISessionService sessionService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest req) {
        log.info("login START | username={}", req.getUsername());
        try {
            LoginResponse result = sessionService.login(req);
            log.info("login END | username={}", req.getUsername());
            return ResponseEntity.ok(ApiResponse.ok(result, "Login successful"));
        } catch (Exception e) {
            log.error("login Exception occurred | username={}", req.getUsername(), e);
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(ApiResponse.ok(null, "Logged out"));
    }
}
