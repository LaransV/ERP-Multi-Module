package com.nexerp.modules.auth.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.auth.repository.interfaces.ICompanyDirectoryRepository;
import com.nexerp.modules.auth.sql.AuthSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CompanyDirectoryRepository implements ICompanyDirectoryRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getUserCompanies(Integer userId) {
        // Original proc returns ALL active companies regardless of userId
        // (flat list, no CorporateId grouping) - reproduced as-is.
        return jdbc.queryList(AuthSql.GET_USER_COMPANIES, new HashMap<>());
    }
}
