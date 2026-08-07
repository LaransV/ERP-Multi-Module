package com.nexerp.modules.inventory.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.inventory.dto.request.AdjustRequest;
import com.nexerp.modules.inventory.dto.request.PoRequest;
import com.nexerp.modules.inventory.dto.response.InventoryDashboardResponse;
import com.nexerp.modules.inventory.dto.response.MovementResponse;
import com.nexerp.modules.inventory.dto.response.PoListItem;
import com.nexerp.modules.inventory.dto.response.PoResponse;
import com.nexerp.modules.inventory.dto.response.StockItemResponse;

public interface InventoryService {

    PagedResponse<StockItemResponse> listStock(int page, int size, String filter);
    StockItemResponse getStock(Integer id);

    PagedResponse<MovementResponse> listMovements(int page, int size, Integer productId);
    void adjustStock(AdjustRequest req);

    PagedResponse<PoListItem> listPOs(int page, int size, String status);
    PoResponse getPO(Integer id);
    PoResponse createPO(PoRequest req);
    void approvePO(Integer id);
    void deletePO(Integer id);

    InventoryDashboardResponse getDashboard();
}
