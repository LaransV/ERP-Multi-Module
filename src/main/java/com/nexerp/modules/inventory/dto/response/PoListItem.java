package com.nexerp.modules.inventory.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PoListItem {

        private Integer    poId;
        private String     poNumber;
        private String     vendorName;
        private String     poDate;
        private String     status;
        private BigDecimal totalAmount;
        private String     createdAt;
    
}
