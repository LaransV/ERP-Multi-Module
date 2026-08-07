package com.nexerp.modules.hr.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DesigResponse {

        private Integer desigId;
        private String  desigName;
        private Integer level;
    
}
