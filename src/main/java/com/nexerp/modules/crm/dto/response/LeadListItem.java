package com.nexerp.modules.crm.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class LeadListItem {

        private Integer    leadId;
        private String     leadName;
        private String     company;
        private String     phone;
        private String     source;
        private String     status;
        private String     priority;
        private String     assignedToName;
        private BigDecimal expectedValue;
        private String     nextFollowupDate;
        private String     createdAt;
    
}
