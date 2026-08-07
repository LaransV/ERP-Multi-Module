package com.nexerp.modules.hr.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PayrollProcessRequest {

        @NotNull(message = "Month is required")
        private Integer month;

        @NotNull(message = "Year is required")
        private Integer year;
    
}
