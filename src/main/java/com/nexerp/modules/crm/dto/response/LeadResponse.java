package com.nexerp.modules.crm.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class LeadResponse {

        private Integer    leadId;
        private String     leadName;
        private String     company;
        private String     email;
        private String     phone;
        private String     source;
        private String     status;
        private String     priority;
        private Integer    assignedToId;
        private String     assignedToName;
        private BigDecimal expectedValue;
        private String     expectedCloseDate;
        private String     notes;
        private String     rejectedReason;
        private String     nextFollowupDate;
        private String     lastActivityAt;
        private Integer    convertedToClientId;
        private String     createdAt;
    
}
