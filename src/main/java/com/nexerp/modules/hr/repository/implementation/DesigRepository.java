package com.nexerp.modules.hr.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.hr.repository.interfaces.IDesigRepository;
import com.nexerp.modules.hr.sql.DesigSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DesigRepository implements IDesigRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getDesignations(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryList(DesigSql.GET_DESIGNATIONS, params);
    }
}
