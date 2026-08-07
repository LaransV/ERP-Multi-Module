package com.nexerp.modules.auth.controller;

import com.nexerp.common.ApiResponse;
import com.nexerp.modules.auth.dto.request.ChangePasswordRequest;
import com.nexerp.modules.auth.dto.response.UserInfo;
import com.nexerp.modules.auth.service.interfaces.IProfileService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfo>> me() {
        log.info("me START");
        try {
            UserInfo result = profileService.me();
            log.info("me END");
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("me Exception occurred", e);
            throw e;
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest req) {
        log.info("changePassword START");
        try {
            profileService.changePassword(req);
            log.info("changePassword END");
            return ResponseEntity.ok(ApiResponse.ok(null, "Password changed"));
        } catch (Exception e) {
            log.error("changePassword Exception occurred", e);
            throw e;
        }
    }
}
