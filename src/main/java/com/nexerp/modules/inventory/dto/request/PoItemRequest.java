package com.nexerp.modules.inventory.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PoItemRequest {

        @NotNull(message = "Product ID is required")
        private Integer    productId;

        @NotBlank(message = "Product name is required")
        private String     productName;

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.001", message = "Quantity must be > 0")
        private BigDecimal quantity;

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0", message = "Unit price must be >= 0")
        private BigDecimal unitPrice;

        private BigDecimal totalAmount;
    
}
