package com.nexerp.modules.finance.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GetAllInvoiceDto {

        private Integer    invoiceId;
        private String     invoiceNumber;
        private Integer    clientId;
        private String     clientName;
        private String     invoiceDate;
        private String     dueDate;
        private String     status;
        private String     paymentStatus;
        private BigDecimal grandTotal;
        private BigDecimal paidAmount;
        private BigDecimal balanceAmount;
        private String     createdAt;
    
}
