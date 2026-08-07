package com.nexerp.modules.hr.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.hr.repository.interfaces.IHRMSDashboardRepository;
import com.nexerp.modules.hr.sql.HRMSDashboardSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class HRMSDashboardRepository implements IHRMSDashboardRepository {

    private final JdbcExecutor jdbc;

    @Override
    public Map<String, Object> getDashboardSummary(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryOne(HRMSDashboardSql.GET_DASHBOARD_SUMMARY, params);
    }

    @Override
    public List<Map<String, Object>> getDeptWiseCount(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryList(HRMSDashboardSql.GET_DEPT_WISE_COUNT, params);
    }
}
