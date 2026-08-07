package com.nexerp.modules.inventory.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.inventory.repository.interfaces.IMovementRepository;
import com.nexerp.modules.inventory.sql.MovementSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MovementRepository implements IMovementRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getMovements(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(MovementSql.GET_MOVEMENTS, params);
    }

    @Override
    public List<Map<String, Object>> getRecentMovements(int limit, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("Limit", limit);
        params.put("CompanyId", companyId);
        return jdbc.queryList(MovementSql.GET_RECENT_MOVEMENTS, params);
    }
}
