package com.nexerp.modules.crm.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CRMDashboardResponse {

        private long       totalLeads;
        private long       newLeads;
        private long       wonLeads;
        private long       lostLeads;
        private double     conversionRate;
        private BigDecimal totalPipelineValue;
        private List<StatusWiseDto>    statusWise;
        private List<FollowupResponse> recentFollowups;
    
}
