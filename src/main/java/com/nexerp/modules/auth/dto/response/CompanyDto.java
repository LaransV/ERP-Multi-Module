package com.nexerp.modules.auth.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class CompanyDto {

        private Integer companyId;
        private String  companyName;
        private String  currency;
        private String  gstin;
    
}
