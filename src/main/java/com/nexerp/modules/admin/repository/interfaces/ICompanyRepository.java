package com.nexerp.modules.admin.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface ICompanyRepository {

    List<Map<String, Object>> getCompanies();
    Map<String, Object> getCompanyById(Integer id);
    Integer insertCompany(Map<String, Object> params);
    void updateCompany(Map<String, Object> params);
    void deleteCompany(Integer id);
}
