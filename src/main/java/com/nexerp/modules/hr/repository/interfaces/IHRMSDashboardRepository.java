package com.nexerp.modules.hr.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IHRMSDashboardRepository {

    Map<String, Object> getDashboardSummary(Integer companyId);
    List<Map<String, Object>> getDeptWiseCount(Integer companyId);
}
