package com.nexerp.modules.finance.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class InvoiceItemRequestDto {

        @NotNull(message = "Product ID is required")
        private Integer    productId;

        @NotBlank(message = "Product name is required")
        private String     productName;
        private String     hsnCode;

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.001", message = "Quantity must be greater than 0")
        private BigDecimal quantity;
        private String     unit;

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0", message = "Unit price must be >= 0")
        private BigDecimal unitPrice;
        private BigDecimal discountPct;
        private BigDecimal discountAmount;
        private BigDecimal taxableAmount;
        private BigDecimal cgstRate;
        private BigDecimal cgstAmount;
        private BigDecimal sgstRate;
        private BigDecimal sgstAmount;
        private BigDecimal igstRate;
        private BigDecimal igstAmount;
        private BigDecimal totalAmount;
    
}
