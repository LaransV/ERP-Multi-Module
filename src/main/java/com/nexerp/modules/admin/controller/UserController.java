package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.request.UserRequest;
import com.nexerp.modules.admin.dto.response.UserResponse;
import com.nexerp.modules.admin.service.interfaces.IUserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        log.info("listUsers START | page={}, size={}, search={}", page, size, search);
        try {
            PagedResponse<UserResponse> result = userService.listUsers(page, size, search);
            log.info("listUsers END | returned={}", result.getContent().size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listUsers Exception occurred | page={}, size={}", page, size, e);
            throw e;
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Integer id) {
        log.info("getUser START | userId={}", id);
        try {
            UserResponse result = userService.getUser(id);
            log.info("getUser END | userId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getUser Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest req) {
        log.info("createUser START | username={}", req.getUsername());
        try {
            UserResponse result = userService.createUser(req);
            log.info("createUser END | newUserId={}", result.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "User created"));
        } catch (Exception e) {
            log.error("createUser Exception occurred | username={}", req.getUsername(), e);
            throw e;
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Integer id, @Valid @RequestBody UserRequest req) {
        log.info("updateUser START | userId={}", id);
        try {
            UserResponse result = userService.updateUser(id, req);
            log.info("updateUser END | userId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "User updated"));
        } catch (Exception e) {
            log.error("updateUser Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    @PatchMapping("/users/{id}/toggle")
    public ResponseEntity<ApiResponse<Void>> toggleUser(@PathVariable Integer id) {
        log.info("toggleUser START | userId={}", id);
        try {
            userService.toggleUser(id);
            log.info("toggleUser END | userId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "User status toggled"));
        } catch (Exception e) {
            log.error("toggleUser Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        log.info("deleteUser START | userId={}", id);
        try {
            userService.deleteUser(id);
            log.info("deleteUser END | userId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "User deleted"));
        } catch (Exception e) {
            log.error("deleteUser Exception occurred | userId={}", id, e);
            throw e;
        }
    }
}
