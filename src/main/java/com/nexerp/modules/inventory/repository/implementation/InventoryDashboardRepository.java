package com.nexerp.modules.inventory.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.inventory.repository.interfaces.IInventoryDashboardRepository;
import com.nexerp.modules.inventory.sql.MovementSql;
import com.nexerp.modules.inventory.sql.StockSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InventoryDashboardRepository implements IInventoryDashboardRepository {

    private final JdbcExecutor jdbc;

    @Override
    public Map<String, Object> getDashboardSummary(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryOne(MovementSql.GET_DASHBOARD_SUMMARY, params);
    }

    @Override
    public List<Map<String, Object>> getLowStockItems(int limit, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("Limit", limit);
        params.put("CompanyId", companyId);
        return jdbc.queryList(StockSql.GET_LOW_STOCK_ITEMS, params);
    }
}
