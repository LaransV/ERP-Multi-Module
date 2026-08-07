package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.request.RoleRequest;
import com.nexerp.modules.admin.dto.response.RoleResponse;
import com.nexerp.modules.admin.service.interfaces.IRoleService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> listRoles() {
        log.info("listRoles START");
        try {
            List<RoleResponse> result = roleService.listRoles();
            log.info("listRoles END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listRoles Exception occurred", e);
            throw e;
        }
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRole(@PathVariable Integer id) {
        log.info("getRole START | roleId={}", id);
        try {
            RoleResponse result = roleService.getRole(id);
            log.info("getRole END | roleId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getRole Exception occurred | roleId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody RoleRequest req) {
        log.info("createRole START | roleName={}", req.getRoleName());
        try {
            RoleResponse result = roleService.createRole(req);
            log.info("createRole END | newRoleId={}", result.getRoleId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Role created"));
        } catch (Exception e) {
            log.error("createRole Exception occurred | roleName={}", req.getRoleName(), e);
            throw e;
        }
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Integer id, @Valid @RequestBody RoleRequest req) {
        log.info("updateRole START | roleId={}", id);
        try {
            RoleResponse result = roleService.updateRole(id, req);
            log.info("updateRole END | roleId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Role updated"));
        } catch (Exception e) {
            log.error("updateRole Exception occurred | roleId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Integer id) {
        log.info("deleteRole START | roleId={}", id);
        try {
            roleService.deleteRole(id);
            log.info("deleteRole END | roleId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Role deleted"));
        } catch (Exception e) {
            log.error("deleteRole Exception occurred | roleId={}", id, e);
            throw e;
        }
    }
}
