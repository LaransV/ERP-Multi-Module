package com.nexerp.modules.crm.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ActivityResponse {

        private Integer activityId;
        private Integer leadId;
        private Integer clientId;
        private String  entityName;
        private String  activityType;
        private String  title;
        private String  description;
        private String  scheduledAt;
        private String  completedAt;
        private String  status;
        private String  createdBy;
        private String  createdAt;
    
}
