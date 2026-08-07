package com.nexerp.modules.crm.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class LeadRequest {

        @NotBlank(message = "Lead name is required")
        private String     leadName;
        private String     company;
        private String     email;

        @NotBlank(message = "Phone is required")
        private String     phone;
        private String     source;
        private String     status;
        private String     priority;
        private Integer    assignedToId;
        private BigDecimal expectedValue;
        private String     expectedCloseDate;
        private String     notes;
        private String     rejectedReason;
    
}
