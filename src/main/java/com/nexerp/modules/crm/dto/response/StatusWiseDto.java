package com.nexerp.modules.crm.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class StatusWiseDto {

        private String     status;
        private long       count;
        private BigDecimal value;
    
}
