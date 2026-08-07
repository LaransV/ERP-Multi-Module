package com.nexerp.modules.inventory.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class StockItemResponse {

        private Integer    stockId;
        private Integer    productId;
        private String     productName;
        private String     productCode;
        private String     hsnCode;
        private String     unit;
        private String     categoryName;
        private BigDecimal openingStock;
        private BigDecimal currentStock;
        private BigDecimal reservedStock;
        private BigDecimal availableStock;
        private BigDecimal reorderLevel;
        private BigDecimal purchasePrice;
        private BigDecimal stockValue;
        private String     lastUpdated;
    
}
