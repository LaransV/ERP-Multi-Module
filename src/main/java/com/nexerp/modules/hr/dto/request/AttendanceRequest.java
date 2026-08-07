package com.nexerp.modules.hr.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AttendanceRequest {

        @NotNull(message = "Employee is required")
        private Integer empId;

        @NotBlank(message = "Attendance date is required")
        private String  attendanceDate;
        private String  checkIn;
        private String  checkOut;

        @NotBlank(message = "Status is required")
        private String  status;
        private String  source;
        private String  notes;
    
}
