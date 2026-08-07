package com.nexerp.modules.crm.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.crm.repository.interfaces.ICRMDashboardRepository;
import com.nexerp.modules.crm.sql.CRMDashboardSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CRMDashboardRepository implements ICRMDashboardRepository {

    private final JdbcExecutor jdbc;

    @Override
    public Map<String, Object> getDashboardSummary(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryOne(CRMDashboardSql.GET_DASHBOARD_SUMMARY, params);
    }

    @Override
    public List<Map<String, Object>> getStatusWise(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryList(CRMDashboardSql.GET_STATUS_WISE, params);
    }
}
