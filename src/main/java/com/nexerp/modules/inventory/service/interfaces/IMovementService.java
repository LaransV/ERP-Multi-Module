package com.nexerp.modules.inventory.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.inventory.dto.response.MovementResponse;

public interface IMovementService {
    PagedResponse<MovementResponse> listMovements(int page, int size, Integer productId);
}
