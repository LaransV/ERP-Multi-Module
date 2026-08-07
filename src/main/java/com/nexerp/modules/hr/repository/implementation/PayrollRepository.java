package com.nexerp.modules.hr.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.hr.repository.interfaces.IPayrollRepository;
import com.nexerp.modules.hr.sql.PayrollSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PayrollRepository implements IPayrollRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getPayroll(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(PayrollSql.GET_PAYROLL, params);
    }

    // companyId unused - original proc (usp_HR_GetPayrollById) never
    // filtered by CompanyId either.
    @Override
    public Map<String, Object> getPayrollById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("PayrollId", id);
        return jdbc.queryOne(PayrollSql.GET_PAYROLL_BY_ID, params);
    }

    @Override
    public void processPayroll(Integer month, Integer year, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("Month", month);
        params.put("Year", year);
        params.put("CompanyId", companyId);
        jdbc.execute(PayrollSql.PROCESS_PAYROLL, params);
    }

    @Override
    public void markPayrollPaid(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("PayrollId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(PayrollSql.MARK_PAYROLL_PAID, params);
    }
}
