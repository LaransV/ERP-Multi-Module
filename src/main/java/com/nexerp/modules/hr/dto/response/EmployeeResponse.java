package com.nexerp.modules.hr.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class EmployeeResponse {

        private Integer    empId;
        private String     empCode;
        private String     firstName;
        private String     lastName;
        private String     fullName;
        private String     email;
        private String     phone;
        private Integer    deptId;
        private String     deptName;
        private Integer    desigId;
        private String     desigName;
        private String     dateOfJoining;
        private String     dateOfBirth;
        private String     gender;
        private String     employmentType;
        private String     status;
        private BigDecimal basicSalary;
        private String     pfNumber;
        private String     esiNumber;
        private String     panNumber;
        private Integer    reportingManagerId;
        private String     reportingManagerName;
        private String     address;
        private String     city;
        private String     state;
        private String     createdAt;
    
}
