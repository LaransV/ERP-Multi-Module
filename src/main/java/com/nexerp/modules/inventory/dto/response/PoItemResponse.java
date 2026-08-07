package com.nexerp.modules.inventory.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PoItemResponse {

        private Integer    poItemId;
        private Integer    productId;
        private String     productName;
        private BigDecimal quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalAmount;
    
}
