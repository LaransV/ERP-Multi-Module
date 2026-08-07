package com.nexerp.modules.inventory.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IMovementRepository {

    List<Map<String, Object>> getMovements(Map<String, Object> params);
    List<Map<String, Object>> getRecentMovements(int limit, Integer companyId);
}
