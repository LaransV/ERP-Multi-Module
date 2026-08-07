package com.nexerp.modules.finance.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.finance.dto.request.ShipToAddressRequestDto;
import com.nexerp.modules.finance.dto.response.ShipToAddressResponseDto;
import com.nexerp.modules.finance.repository.interfaces.IShipToAddressRepository;
import com.nexerp.modules.finance.service.interfaces.IShipToAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipToAddressService implements IShipToAddressService {

    private final IShipToAddressRepository repository;
    private final AuthService auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public List<ShipToAddressResponseDto> listShipToAddresses() {
        List<ShipToAddressResponseDto> out = new ArrayList<>();
        for (Map<String, Object> r : repository.getShipToAddresses(cid())) {
            ShipToAddressResponseDto dto = new ShipToAddressResponseDto();
            dto.setShipToAddressId((Integer) r.get("ship_to_address_id"));
            dto.setName((String) r.get("name"));
            dto.setAddressLine1((String) r.get("address_line1"));
            dto.setAddressLine2((String) r.get("address_line2"));
            dto.setShippingState((String) r.get("shipping_state"));
            dto.setPincode((String) r.get("pincode"));
            dto.setGstin((String) r.get("gstin"));
            out.add(dto);
        }
        return out;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShipToAddressResponseDto createShipToAddress(ShipToAddressRequestDto req) {
        Map<String, Object> params = new HashMap<>();
        params.put("Name", req.getName());
        params.put("AddressLine1", req.getAddressLine1());
        params.put("AddressLine2", req.getAddressLine2());
        params.put("ShippingState", req.getShippingState());
        params.put("Pincode", req.getPincode());
        params.put("Gstin", req.getGstin());
        params.put("CompanyId", cid());
        Integer id = repository.insertShipToAddress(params);

        ShipToAddressResponseDto dto = new ShipToAddressResponseDto();
        dto.setShipToAddressId(id);
        dto.setName(req.getName());
        dto.setAddressLine1(req.getAddressLine1());
        dto.setAddressLine2(req.getAddressLine2());
        dto.setShippingState(req.getShippingState());
        dto.setPincode(req.getPincode());
        dto.setGstin(req.getGstin());
        return dto;
    }
}
