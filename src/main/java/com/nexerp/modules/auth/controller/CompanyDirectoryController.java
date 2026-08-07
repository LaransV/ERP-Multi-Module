package com.nexerp.modules.auth.controller;

import com.nexerp.common.ApiResponse;
import com.nexerp.modules.auth.dto.response.CompanyDto;
import com.nexerp.modules.auth.service.interfaces.ICompanyDirectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CompanyDirectoryController {

    private final ICompanyDirectoryService companyDirectoryService;

    /**
     * Returns all active companies (flat list).
     * Frontend calls this once after login to populate the company switcher.
     */
    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<List<CompanyDto>>> getMyCompanies() {
        log.info("getMyCompanies START");
        try {
            List<CompanyDto> result = companyDirectoryService.getMyCompanies();
            log.info("getMyCompanies END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getMyCompanies Exception occurred", e);
            throw e;
        }
    }
}
