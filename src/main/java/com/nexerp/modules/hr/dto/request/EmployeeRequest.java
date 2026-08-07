package com.nexerp.modules.hr.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class EmployeeRequest {

        @NotBlank(message = "Employee code is required")
        private String     empCode;

        @NotBlank(message = "First name is required")
        private String     firstName;

        @NotBlank(message = "Last name is required")
        private String     lastName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String     email;

        @NotBlank(message = "Phone is required")
        private String     phone;

        @NotNull(message = "Department is required")
        private Integer    deptId;

        @NotNull(message = "Designation is required")
        private Integer    desigId;

        @NotBlank(message = "Date of joining is required")
        private String     dateOfJoining;
        private String     dateOfBirth;
        private String     gender;
        private String     employmentType;

        @NotNull(message = "Basic salary is required")
        private BigDecimal basicSalary;

        private String     pfNumber;
        private String     esiNumber;
        private String     panNumber;
        private Integer    reportingManagerId;
        private String     address;
        private String     city;
        private String     state;
        private String     status;
    
}
