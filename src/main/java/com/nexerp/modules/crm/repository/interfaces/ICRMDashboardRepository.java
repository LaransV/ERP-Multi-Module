package com.nexerp.modules.crm.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface ICRMDashboardRepository {

    Map<String, Object> getDashboardSummary(Integer companyId);
    List<Map<String, Object>> getStatusWise(Integer companyId);
}
