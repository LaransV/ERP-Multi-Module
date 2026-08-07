package com.nexerp.modules.finance.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class FinanceDashboardResponseDto {

        @Data
        public static class MonthlyPoint {
                private String     month;
                private BigDecimal invoiced;
                private BigDecimal collected;
        }

        private long       totalInvoices;
        private BigDecimal totalRevenue;
        private long       dueCount;
        private BigDecimal dueAmount;
        private long       overdueCount;
        private BigDecimal overdueAmount;
        private long       paidCount;
        private BigDecimal paidAmount;
        private List<MonthlyPoint> monthlyData;
    
}
