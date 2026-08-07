package com.nexerp.modules.inventory.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PoResponse {

        private Integer    poId;
        private String     poNumber;
        private Integer    vendorId;
        private String     vendorName;
        private String     poDate;
        private String     expectedDate;
        private String     status;
        private List<PoItemResponse> items;
        private BigDecimal totalAmount;
        private String     notes;
        private String     createdAt;
    
}
