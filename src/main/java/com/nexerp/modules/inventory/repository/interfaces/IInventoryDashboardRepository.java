package com.nexerp.modules.inventory.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IInventoryDashboardRepository {

    Map<String, Object> getDashboardSummary(Integer companyId);
    List<Map<String, Object>> getLowStockItems(int limit, Integer companyId);
}
