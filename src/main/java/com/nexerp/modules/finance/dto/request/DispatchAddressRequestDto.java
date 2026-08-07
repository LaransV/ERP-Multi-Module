package com.nexerp.modules.finance.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class DispatchAddressRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String dispatchState;
    private String pincode;
}
