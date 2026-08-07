package com.nexerp.modules.finance.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.finance.repository.interfaces.IFinanceDashboardRepository;
import com.nexerp.modules.finance.sql.DashboardSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FinanceDashboardRepository implements IFinanceDashboardRepository {

    private final JdbcExecutor jdbc;

    @Override
    public Map<String, Object> getDashboardSummary(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryOne(DashboardSql.GET_DASHBOARD_SUMMARY, params);
    }

    @Override
    public List<Map<String, Object>> getMonthlyRevenue(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryList(DashboardSql.GET_MONTHLY_REVENUE, params);
    }
}
