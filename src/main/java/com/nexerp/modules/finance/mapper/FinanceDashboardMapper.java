package com.nexerp.modules.finance.mapper;

import com.nexerp.modules.finance.dto.response.FinanceDashboardResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FinanceDashboardMapper {

    // ── Dashboard ────────────────────────────
    public FinanceDashboardResponseDto toDashboard(Map<String, Object> s, List<Map<String, Object>> monthly) {
        FinanceDashboardResponseDto d = new FinanceDashboardResponseDto();
        if (s != null) {
            d.setTotalInvoices(toLong(s.get("TotalInvoices")));
            d.setTotalRevenue(toBD(s.get("TotalRevenue")));
            d.setDueCount(toLong(s.get("DueCount")));
            d.setDueAmount(toBD(s.get("DueAmount")));
            d.setOverdueCount(toLong(s.get("OverdueCount")));
            d.setOverdueAmount(toBD(s.get("OverdueAmount")));
            d.setPaidCount(toLong(s.get("PaidCount")));
            d.setPaidAmount(toBD(s.get("paid_amount")));
        }
        d.setMonthlyData(monthly.stream().map(r -> {
            FinanceDashboardResponseDto.MonthlyPoint pt = new FinanceDashboardResponseDto.MonthlyPoint();
            pt.setMonth((String) r.get("month"));
            pt.setInvoiced(toBD(r.get("Invoiced")));
            pt.setCollected(toBD(r.get("Collected")));
            return pt;
        }).collect(Collectors.toList()));
        return d;
    }

    public long        toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o)  { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }

}
