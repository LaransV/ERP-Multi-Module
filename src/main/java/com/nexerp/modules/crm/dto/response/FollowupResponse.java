package com.nexerp.modules.crm.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class FollowupResponse {

        private Integer followupId;
        private Integer leadId;
        private String  leadName;
        private String  scheduledAt;
        private String  completedAt;
        private String  notes;
        private String  followupType;
        private String  status;
        private String  createdBy;
    
}
