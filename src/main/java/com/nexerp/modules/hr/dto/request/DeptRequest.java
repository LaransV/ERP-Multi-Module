package com.nexerp.modules.hr.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DeptRequest {

        @NotBlank(message = "Department name is required")
        private String  deptName;
        private Integer headId;
    
}
