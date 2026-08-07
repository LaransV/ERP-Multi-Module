package com.nexerp.modules.finance.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IFinanceDashboardRepository {
    // ── Dashboard ────────────────────────────
    Map<String, Object> getDashboardSummary(Integer companyId);
    List<Map<String, Object>> getMonthlyRevenue(Integer companyId);
}
