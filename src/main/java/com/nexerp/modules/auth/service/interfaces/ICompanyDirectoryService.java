package com.nexerp.modules.auth.service.interfaces;

import com.nexerp.modules.auth.dto.response.CompanyDto;

import java.util.List;

public interface ICompanyDirectoryService {
    List<CompanyDto> getMyCompanies();
}
