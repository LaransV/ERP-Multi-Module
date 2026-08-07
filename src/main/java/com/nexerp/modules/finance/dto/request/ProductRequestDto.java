package com.nexerp.modules.finance.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDto {

        @NotBlank(message = "Product name is required")
        private String     productName;
        private String     productCode;
        private String     hsnCode;
        private String     productType;
        private String     unit;
        private BigDecimal taxRate;
        private BigDecimal purchasePrice;
        private BigDecimal salePrice;
        private String     categoryName;
        private String     groupName;
    
}
