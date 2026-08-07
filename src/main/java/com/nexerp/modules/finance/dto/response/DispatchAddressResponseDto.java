package com.nexerp.modules.finance.dto.response;

import lombok.Data;

@Data
public class DispatchAddressResponseDto {
    private Integer dispatchAddressId;
    private String  name;
    private String  addressLine1;
    private String  addressLine2;
    private String  dispatchState;
    private String  pincode;
}
