package com.nexerp.modules.inventory.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.inventory.dto.request.PoRequest;
import com.nexerp.modules.inventory.dto.response.PoListItem;
import com.nexerp.modules.inventory.dto.response.PoResponse;

public interface IPurchaseOrderService {
    PagedResponse<PoListItem> listPOs(int page, int size, String status);
    PoResponse getPO(Integer id);
    PoResponse createPO(PoRequest req);
    void approvePO(Integer id);
    void deletePO(Integer id);
}
