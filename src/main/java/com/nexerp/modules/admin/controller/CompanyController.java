package com.nexerp.modules.admin.controller;

import com.nexerp.common.*;
import com.nexerp.modules.admin.dto.request.CompanyRequest;
import com.nexerp.modules.admin.dto.response.CompanyResponse;
import com.nexerp.modules.admin.service.interfaces.ICompanyService;
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
public class CompanyController {

    private final ICompanyService companyService;

    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> listCompanies() {
        log.info("listCompanies START");
        try {
            List<CompanyResponse> result = companyService.listCompanies();
            log.info("listCompanies END | count={}", result.size());
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("listCompanies Exception occurred", e);
            throw e;
        }
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompany(@PathVariable Integer id) {
        log.info("getCompany START | companyId={}", id);
        try {
            CompanyResponse result = companyService.getCompany(id);
            log.info("getCompany END | companyId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result));
        } catch (Exception e) {
            log.error("getCompany Exception occurred | companyId={}", id, e);
            throw e;
        }
    }

    @PostMapping("/companies")
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
            @Valid @RequestBody CompanyRequest req) {
        log.info("createCompany START | companyName={}", req.getCompanyName());
        try {
            CompanyResponse result = companyService.createCompany(req);
            log.info("createCompany END | newCompanyId={}", result.getCompanyId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok(result, "Company created"));
        } catch (Exception e) {
            log.error("createCompany Exception occurred | companyName={}", req.getCompanyName(), e);
            throw e;
        }
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> updateCompany(
            @PathVariable Integer id, @Valid @RequestBody CompanyRequest req) {
        log.info("updateCompany START | companyId={}", id);
        try {
            CompanyResponse result = companyService.updateCompany(id, req);
            log.info("updateCompany END | companyId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(result, "Company updated"));
        } catch (Exception e) {
            log.error("updateCompany Exception occurred | companyId={}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Integer id) {
        log.info("deleteCompany START | companyId={}", id);
        try {
            companyService.deleteCompany(id);
            log.info("deleteCompany END | companyId={}", id);
            return ResponseEntity.ok(ApiResponse.ok(null, "Company deleted"));
        } catch (Exception e) {
            log.error("deleteCompany Exception occurred | companyId={}", id, e);
            throw e;
        }
    }
}
