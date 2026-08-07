package com.nexerp.modules.crm.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class LeadStatusRequest {

        @NotBlank(message = "Status is required")
        private String status;
    
}
