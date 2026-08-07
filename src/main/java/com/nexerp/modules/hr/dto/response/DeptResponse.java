package com.nexerp.modules.hr.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DeptResponse {

        private Integer deptId;
        private String  deptName;
        private String  headName;
        private int     employeeCount;
    
}
