package com.nexerp.modules.inventory.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AdjustRequest {

        @NotNull(message = "Product is required")
        private Integer    productId;

        @NotNull(message = "Quantity is required")
        private BigDecimal quantity;

        @NotBlank(message = "Type is required (IN / OUT / ADJUSTMENT)")
        private String     type;

        private String notes;
    
}
