package com.nexerp.modules.inventory.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IPurchaseOrderRepository {

    List<Map<String, Object>> getPurchaseOrders(Map<String, Object> params);
    Map<String, Object> getPOById(Integer id, Integer companyId);
    List<Map<String, Object>> getPOItems(Integer poId);
    Integer insertPO(Map<String, Object> params);
    void insertPOItem(Map<String, Object> params);
    void approvePO(Integer id, Integer companyId);
    void deletePO(Integer id, Integer companyId);
}
