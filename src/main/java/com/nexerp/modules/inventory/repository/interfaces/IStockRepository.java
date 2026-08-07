package com.nexerp.modules.inventory.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IStockRepository {

    List<Map<String, Object>> getStock(Map<String, Object> params);
    Map<String, Object> getStockById(Integer id, Integer companyId);
    void adjustStock(Map<String, Object> params);
}
