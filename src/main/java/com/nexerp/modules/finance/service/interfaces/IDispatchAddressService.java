package com.nexerp.modules.finance.service.interfaces;

import com.nexerp.modules.finance.dto.request.DispatchAddressRequestDto;
import com.nexerp.modules.finance.dto.response.DispatchAddressResponseDto;

import java.util.List;

public interface IDispatchAddressService {
    List<DispatchAddressResponseDto> listDispatchAddresses();
    DispatchAddressResponseDto createDispatchAddress(DispatchAddressRequestDto req);
}
