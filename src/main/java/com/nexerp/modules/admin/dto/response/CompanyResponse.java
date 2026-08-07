package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class CompanyResponse {

        private Integer companyId;
        private String  companyName;
        private String  currency;
        private String  gstin;
        private String  address;
        private String  phone;
        private String  email;
        private boolean isActive;
        private String  createdAt;
    
}
