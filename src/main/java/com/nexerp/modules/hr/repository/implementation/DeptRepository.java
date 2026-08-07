package com.nexerp.modules.hr.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.hr.repository.interfaces.IDeptRepository;
import com.nexerp.modules.hr.sql.DeptSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DeptRepository implements IDeptRepository {

    private final JdbcExecutor jdbc;

    // companyId unused - original proc (usp_HR_GetDepartments) had no
    // @CompanyId parameter and returned all departments company-wide.
    @Override
    public List<Map<String, Object>> getDepartments(Integer companyId) {
        return jdbc.queryList(DeptSql.GET_DEPARTMENTS, new HashMap<>());
    }

    @Override
    public Integer insertDepartment(Map<String, Object> params) {
        return jdbc.executeAndGetId(DeptSql.INSERT_DEPARTMENT, params);
    }

    @Override
    public void deleteDepartment(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("DeptId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(DeptSql.DELETE_DEPARTMENT, params);
    }
}
