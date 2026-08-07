package com.nexerp.modules.admin.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class CompanyRequest {

        @NotBlank(message = "Company name is required")
        private String  companyName;
        private String  currency    = "INR";
        private String  gstin;
        private String  address;
        private String  phone;
        private String  email;
        private Boolean isActive    = true;
    
}
