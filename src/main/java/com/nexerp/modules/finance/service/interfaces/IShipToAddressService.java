package com.nexerp.modules.finance.service.interfaces;

import com.nexerp.modules.finance.dto.request.ShipToAddressRequestDto;
import com.nexerp.modules.finance.dto.response.ShipToAddressResponseDto;

import java.util.List;

public interface IShipToAddressService {
    List<ShipToAddressResponseDto> listShipToAddresses();
    ShipToAddressResponseDto createShipToAddress(ShipToAddressRequestDto req);
}
