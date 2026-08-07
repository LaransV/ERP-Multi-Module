package com.nexerp.modules.auth.service.implementation;

import com.nexerp.modules.auth.dto.response.CompanyDto;
import com.nexerp.modules.auth.mapper.CompanyDirectoryMapper;
import com.nexerp.modules.auth.repository.interfaces.ICompanyDirectoryRepository;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.auth.service.interfaces.ICompanyDirectoryService;
import com.nexerp.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyDirectoryService implements ICompanyDirectoryService {

    private final ICompanyDirectoryRepository repository;
    private final CompanyDirectoryMapper      mapper;
    private final AuthService                 auth;

    @Override
    public List<CompanyDto> getMyCompanies() {
        log.info("getMyCompanies START");
        try {
            UserPrincipal p = auth.currentPrincipal();
            List<CompanyDto> result = mapper.toCompanies(repository.getUserCompanies(p.getUserId()));
            log.info("getMyCompanies END | count={}", result.size());
            return result;
        } catch (Exception e) {
            log.error("getMyCompanies | Exception occurred", e);
            throw e;
        }
    }
}
