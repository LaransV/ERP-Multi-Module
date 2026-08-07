package com.nexerp.modules.inventory.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InventoryDashboardResponse {

        private long       totalItems;
        private BigDecimal totalStockValue;
        private long       lowStockCount;
        private long       outOfStockCount;
        private List<MovementResponse>  recentMovements;
        private List<StockItemResponse> lowStockItems;
    
}
