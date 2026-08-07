package com.nexerp.modules.inventory.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PoRequest {

        @NotNull(message = "Vendor ID is required")
        private Integer    vendorId;

        @NotBlank(message = "PO date is required")
        private String     poDate;
        private String     expectedDate;

        @NotEmpty(message = "At least one item is required")
        private List<PoItemRequest> items;

        private BigDecimal totalAmount;
        private String     notes;
        private String     supplierName; // display only, not sent to SP
    
}
