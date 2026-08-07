package com.nexerp.modules.inventory.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.inventory.repository.interfaces.IStockRepository;
import com.nexerp.modules.inventory.sql.StockSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockRepository implements IStockRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getStock(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(StockSql.GET_STOCK, params);
    }

    @Override
    public Map<String, Object> getStockById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("StockId", id);
        params.put("CompanyId", companyId);
        return jdbc.queryOne(StockSql.GET_STOCK_BY_ID, params);
    }

    /**
     * usp_Inv_AdjustStock did an upsert (IF EXISTS/UPDATE/ELSE/INSERT) plus a
     * movement-log insert in one proc call. Reproduced as an
     * INSERT ... ON CONFLICT DO UPDATE (against UQ_StockLevels_ProductId)
     * followed by the movement log insert - stays atomic because this only
     * runs inside a @Transactional service method. The NEWID()-based
     * reference number is generated here in Java instead of relying on a
     * Postgres UUID extension.
     */
    @Override
    public void adjustStock(Map<String, Object> params) {
        jdbc.execute(StockSql.UPSERT_STOCK_LEVEL, params);

        Map<String, Object> moveParams = new HashMap<>(params);
        moveParams.put("RefNum", "ADJ-" + UUID.randomUUID());
        jdbc.execute(StockSql.INSERT_STOCK_MOVEMENT, moveParams);
    }
}
