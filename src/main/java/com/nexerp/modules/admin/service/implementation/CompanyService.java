package com.nexerp.modules.admin.service.implementation;

import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.admin.dto.request.CompanyRequest;
import com.nexerp.modules.admin.dto.response.CompanyResponse;
import com.nexerp.modules.admin.mapper.CompanyMapper;
import com.nexerp.modules.admin.repository.interfaces.ICompanyRepository;
import com.nexerp.modules.admin.service.interfaces.ICompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final ICompanyRepository repository;
    private final CompanyMapper      mapper;

    @Override
    public List<CompanyResponse> listCompanies() {
        log.info("listCompanies START");
        try {
            List<CompanyResponse> result = repository.getCompanies()
                .stream().map(mapper::toCompany).collect(Collectors.toList());
            log.info("listCompanies END | count={}", result.size());
            return result;
        } catch (Exception e) { log.error("listCompanies | Exception occurred", e); throw e; }
    }

    @Override
    public CompanyResponse getCompany(Integer id) {
        log.info("getCompany START | companyId={}", id);
        try {
            Map<String, Object> row = repository.getCompanyById(id);
            if (row == null) throw new ResourceNotFoundException("Company", id);
            return mapper.toCompany(row);
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getCompany | Exception occurred | companyId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyResponse createCompany(CompanyRequest req) {
        log.info("createCompany START | companyName={}", req.getCompanyName());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("CompanyName", req.getCompanyName());
            params.put("Currency",    req.getCurrency() != null ? req.getCurrency() : "INR");
            params.put("Gstin",       req.getGstin() != null ? req.getGstin() : "");
            params.put("Address",     req.getAddress() != null ? req.getAddress() : "");
            params.put("Phone",       req.getPhone() != null ? req.getPhone() : "");
            params.put("Email",       req.getEmail() != null ? req.getEmail() : "");
            params.put("IsActive",    req.getIsActive() != null ? req.getIsActive() : true);
            Integer newId = repository.insertCompany(params);
            return getCompany(newId);
        } catch (Exception e) { log.error("createCompany | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyResponse updateCompany(Integer id, CompanyRequest req) {
        log.info("updateCompany START | companyId={}", id);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("CompanyId",   id);
            params.put("CompanyName", req.getCompanyName());
            params.put("Currency",    req.getCurrency() != null ? req.getCurrency() : "INR");
            params.put("Gstin",       req.getGstin() != null ? req.getGstin() : "");
            params.put("Address",     req.getAddress() != null ? req.getAddress() : "");
            params.put("Phone",       req.getPhone() != null ? req.getPhone() : "");
            params.put("Email",       req.getEmail() != null ? req.getEmail() : "");
            params.put("IsActive",    req.getIsActive() != null ? req.getIsActive() : true);
            repository.updateCompany(params);
            return getCompany(id);
        } catch (Exception e) { log.error("updateCompany | Exception occurred | companyId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(Integer id) {
        log.info("deleteCompany START | companyId={}", id);
        try {
            repository.deleteCompany(id);
            log.info("deleteCompany END | companyId={}", id);
        } catch (Exception e) { log.error("deleteCompany | Exception occurred | companyId={}", id, e); throw e; }
    }
}
