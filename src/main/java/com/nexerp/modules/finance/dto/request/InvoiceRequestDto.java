package com.nexerp.modules.finance.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceRequestDto {

        @NotNull(message = "Client is required")
        private Integer    clientId;

        @NotBlank(message = "Invoice date is required")
        private String     invoiceDate;

        @NotBlank(message = "Due date is required")
        private String     dueDate;

        @NotEmpty(message = "At least one item is required")
        private List<InvoiceItemRequestDto> items;

        private BigDecimal subtotal;
        private BigDecimal discountAmount;
        private BigDecimal taxableAmount;
        private BigDecimal cgstTotal;
        private BigDecimal sgstTotal;
        private BigDecimal igstTotal;
        private BigDecimal taxTotal;
        private BigDecimal tdsPct;
        private BigDecimal tdsAmount;
        private BigDecimal roundOff;
        private BigDecimal grandTotal;
        private boolean    interstate;
        private String     notes;
        private String     terms;

        // ── Basic Info tab (new) ──────────────────────
        private String     supplierRefNo;
        private String     eWayBillNo;
        private boolean    generateEWayBill;
        private String     dcNo;
        private String     dcDate;
        private String     selectDc;
        private String     vehicleNo;
        private String     lrNo;
        private java.math.BigDecimal distance;
        private String     transporterId;
        private String     delThrough;
        private String     delDestn;
        private String     orderNo;
        private String     orderDate;
        private String     soNo;
        private String     currency;

        // ── Dispatch From / Ship To tabs (new) ────────
        private Integer    dispatchAddressId;
        private Integer    shipToAddressId;


        @Data
        public static class StatusUpdateRequest {
                @NotBlank(message = "Status is required")
                private String status;
        }
    
}
