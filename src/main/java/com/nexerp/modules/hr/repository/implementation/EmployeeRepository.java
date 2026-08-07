package com.nexerp.modules.hr.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.hr.repository.interfaces.IEmployeeRepository;
import com.nexerp.modules.hr.sql.EmployeeSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository implements IEmployeeRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getEmployees(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(EmployeeSql.GET_EMPLOYEES, params);
    }

    // companyId intentionally unused in the query - the original proc
    // (usp_HR_GetEmployeeById) never filtered by CompanyId either.
    @Override
    public Map<String, Object> getEmployeeById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("EmpId", id);
        return jdbc.queryOne(EmployeeSql.GET_EMPLOYEE_BY_ID, params);
    }

    @Override
    public Integer insertEmployee(Map<String, Object> params) {
        return jdbc.executeAndGetId(EmployeeSql.INSERT_EMPLOYEE, params);
    }

    @Override
    public void updateEmployee(Map<String, Object> params) {
        jdbc.execute(EmployeeSql.UPDATE_EMPLOYEE, params);
    }

    @Override
    public void deleteEmployee(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("EmpId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(EmployeeSql.DELETE_EMPLOYEE, params);
    }
}
