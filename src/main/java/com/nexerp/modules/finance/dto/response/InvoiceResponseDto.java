package com.nexerp.modules.finance.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceResponseDto {

        private Integer    invoiceId;
        private String     invoiceNumber;
        private Integer    clientId;
        private String     clientName;
        private String     invoiceDate;
        private String     dueDate;
        private String     status;
        private String     paymentStatus;
        private List<InvoiceItemResponseDto> items;
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
        private BigDecimal paidAmount;
        private BigDecimal balanceAmount;
        private boolean    isInterstate;
        private String     notes;
        private String     terms;
        private String     createdAt;

        // ── Basic Info tab (new) ──────────────────────
        private String     supplierRefNo;
        private String     eWayBillNo;
        private boolean    generateEWayBill;
        private String     dcNo;
        private String     dcDate;
        private String     selectDc;
        private String     vehicleNo;
        private String     lrNo;
        private BigDecimal distance;
        private String     transporterId;
        private String     delThrough;
        private String     delDestn;
        private String     orderNo;
        private String     orderDate;
        private String     soNo;
        private String     currency;

        // ── Dispatch From tab (new) ────────────────────
        private Integer    dispatchAddressId;
        private String     dispatchName;
        private String     dispatchAddressLine1;
        private String     dispatchAddressLine2;
        private String     dispatchStateName;
        private String     dispatchPincode;

        // ── Ship To tab (new) ─────────────────────────
        private Integer    shipToAddressId;
        private String     shipToName;
        private String     shipToAddressLine1;
        private String     shipToAddressLine2;
        private String     shipToStateName;
        private String     shipToPincode;
        private String     shipToGstin;

}
