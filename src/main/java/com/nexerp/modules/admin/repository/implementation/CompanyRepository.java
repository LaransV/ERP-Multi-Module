package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.ICompanyRepository;
import com.nexerp.modules.admin.sql.CompanySql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CompanyRepository implements ICompanyRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getCompanies() {
        return jdbc.queryList(CompanySql.GET_COMPANIES, new HashMap<>());
    }

    @Override
    public Map<String, Object> getCompanyById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", id);
        return jdbc.queryOne(CompanySql.GET_COMPANY_BY_ID, params);
    }

    @Override
    public Integer insertCompany(Map<String, Object> params) {
        return jdbc.executeAndGetId(CompanySql.INSERT_COMPANY, params);
    }

    @Override
    public void updateCompany(Map<String, Object> params) {
        jdbc.execute(CompanySql.UPDATE_COMPANY, params);
    }

    @Override
    public void deleteCompany(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", id);
        jdbc.execute(CompanySql.DELETE_COMPANY, params);
    }
}
