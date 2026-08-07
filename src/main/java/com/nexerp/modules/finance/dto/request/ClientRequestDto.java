package com.nexerp.modules.finance.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ClientRequestDto {

    @NotBlank(message = "Client name is required")
    private String clientName;
    private String aliasName;
    private String vendorCode;
    private String email;
    private String website;
    private String landlinePhone;
    private String phone;
    private String gstnType;
    private String gstNumber;
    private String panNumber;
    private String address;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String currency;
    private String paymentTerms;
    private Boolean isActive = true;

}
