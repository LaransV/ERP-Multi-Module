package com.nexerp.modules.inventory.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MovementResponse {

        private Integer    movementId;
        private Integer    productId;
        private String     productName;
        private String     movementType;
        private BigDecimal quantity;
        private String     referenceType;
        private String     referenceNumber;
        private String     warehouseFrom;
        private String     warehouseTo;
        private String     notes;
        private String     createdBy;
        private String     createdAt;
    
}
