package com.nexerp.modules.finance.dto.response;

import lombok.Data;

@Data
public class ShipToAddressResponseDto {
    private Integer shipToAddressId;
    private String  name;
    private String  addressLine1;
    private String  addressLine2;
    private String  shippingState;
    private String  pincode;
    private String  gstin;
}
