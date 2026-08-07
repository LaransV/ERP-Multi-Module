package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.modules.admin.dto.request.CompanyRequest;
import com.nexerp.modules.admin.dto.response.CompanyResponse;

import java.util.List;

public interface ICompanyService {
    List<CompanyResponse> listCompanies();
    CompanyResponse getCompany(Integer id);
    CompanyResponse createCompany(CompanyRequest req);
    CompanyResponse updateCompany(Integer id, CompanyRequest req);
    void deleteCompany(Integer id);
}
