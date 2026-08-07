package com.nexerp.modules.hr.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AttendanceResponse {

        private Integer attendanceId;
        private Integer empId;
        private String  empName;
        private String  empCode;
        private String  attendanceDate;
        private String  checkIn;
        private String  checkOut;
        private Integer durationMinutes;
        private String  status;
        private String  source;
        private String  notes;
    
}
