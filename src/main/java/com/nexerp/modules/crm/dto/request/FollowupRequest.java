package com.nexerp.modules.crm.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class FollowupRequest {

        @NotNull(message = "Lead ID is required")
        private Integer leadId;

        @NotBlank(message = "Scheduled date/time is required")
        private String  scheduledAt;

        @NotBlank(message = "Notes are required")
        private String  notes;

        @NotBlank(message = "Followup type is required")
        private String  followupType;

        private String status;
    
}
