package com.nexerp.modules.inventory.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.inventory.repository.interfaces.IPurchaseOrderRepository;
import com.nexerp.modules.inventory.sql.PurchaseOrderSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderRepository implements IPurchaseOrderRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getPurchaseOrders(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(PurchaseOrderSql.GET_PURCHASE_ORDERS, params);
    }

    @Override
    public Map<String, Object> getPOById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("PoId", id);
        params.put("CompanyId", companyId);
        return jdbc.queryOne(PurchaseOrderSql.GET_PO_BY_ID, params);
    }

    @Override
    public List<Map<String, Object>> getPOItems(Integer poId) {
        Map<String, Object> params = new HashMap<>();
        params.put("PoId", poId);
        return jdbc.queryList(PurchaseOrderSql.GET_PO_ITEMS, params);
    }

    /** PO number generated the same way the original proc did (NEWID()-based,
     * not from the unused PoSequence table) - see PurchaseOrderSql for detail. */
    @Override
    public Integer insertPO(Map<String, Object> params) {
        Object companyId = params.get("CompanyId");
        String poNumber = "PO-" + companyId + "-" + UUID.randomUUID().toString().substring(0, 10);
        params.put("PoNumber", poNumber);
        return jdbc.executeAndGetId(PurchaseOrderSql.INSERT_PO, params);
    }

    @Override
    public void insertPOItem(Map<String, Object> params) {
        jdbc.execute(PurchaseOrderSql.INSERT_PO_ITEM, params);
    }

    @Override
    public void approvePO(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("PoId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(PurchaseOrderSql.APPROVE_PO, params);
    }

    // Original proc deletes PurchaseOrderItems first, then the PO itself,
    // both in one call - reproduced as two statements run together; safe
    // because this only runs inside a @Transactional service method.
    @Override
    public void deletePO(Integer id, Integer companyId) {
        Map<String, Object> itemParams = new HashMap<>();
        itemParams.put("PoId", id);
        jdbc.execute(PurchaseOrderSql.DELETE_PO_ITEMS, itemParams);

        Map<String, Object> params = new HashMap<>();
        params.put("PoId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(PurchaseOrderSql.DELETE_PO, params);
    }
}
