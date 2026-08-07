package com.nexerp.modules.crm.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ActivityRequest {

        private Integer leadId;
        private Integer clientId;

        @NotBlank(message = "Entity name is required")
        private String entityName;

        @NotBlank(message = "Activity type is required")
        private String activityType;

        @NotBlank(message = "Title is required")
        private String title;
        private String description;
        private String scheduledAt;
        private String status;
    
}
