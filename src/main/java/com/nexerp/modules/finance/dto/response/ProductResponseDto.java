package com.nexerp.modules.finance.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponseDto {

        private Integer    companyId;
        private Integer    productId;
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
        private boolean    isActive;
        private String     createdAt;
    
}
