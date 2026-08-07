package com.nexerp.modules.finance.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InvoiceItemResponseDto {

        private Integer    itemId;
        private Integer    productId;
        private String     productName;
        private String     hsnCode;
        private BigDecimal quantity;
        private String     unit;
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
