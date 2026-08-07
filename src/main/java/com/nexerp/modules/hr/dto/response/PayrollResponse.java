package com.nexerp.modules.hr.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PayrollResponse {

        private Integer    payrollId;
        private Integer    empId;
        private String     empName;
        private String     empCode;
        private Integer    month;
        private Integer    year;
        private Integer    daysWorked;
        private BigDecimal lopDays;
        private BigDecimal grossSalary;
        private BigDecimal totalDeductions;
        private BigDecimal netSalary;
        private BigDecimal pfEmployee;
        private BigDecimal pfEmployer;
        private BigDecimal esiEmployee;
        private BigDecimal esiEmployer;
        private BigDecimal tdsAmount;
        private BigDecimal professionalTax;
        private String     status;
        private String     processedAt;
        private String     paidAt;
    
}
