package com.nexerp.modules.inventory.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.inventory.dto.request.AdjustRequest;
import com.nexerp.modules.inventory.dto.response.StockItemResponse;

public interface IStockService {
    PagedResponse<StockItemResponse> listStock(int page, int size, String filter);
    StockItemResponse getStock(Integer id);
    void adjustStock(AdjustRequest req);
}
