package com.nexerp.modules.finance.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.finance.dto.request.DispatchAddressRequestDto;
import com.nexerp.modules.finance.dto.response.DispatchAddressResponseDto;
import com.nexerp.modules.finance.repository.interfaces.IDispatchAddressRepository;
import com.nexerp.modules.finance.service.interfaces.IDispatchAddressService;
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
public class DispatchAddressService implements IDispatchAddressService {

    private final IDispatchAddressRepository repository;
    private final AuthService auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public List<DispatchAddressResponseDto> listDispatchAddresses() {
        List<DispatchAddressResponseDto> out = new ArrayList<>();
        for (Map<String, Object> r : repository.getDispatchAddresses(cid())) {
            DispatchAddressResponseDto dto = new DispatchAddressResponseDto();
            dto.setDispatchAddressId((Integer) r.get("dispatch_address_id"));
            dto.setName((String) r.get("name"));
            dto.setAddressLine1((String) r.get("address_line1"));
            dto.setAddressLine2((String) r.get("address_line2"));
            dto.setDispatchState((String) r.get("dispatch_state"));
            dto.setPincode((String) r.get("pincode"));
            out.add(dto);
        }
        return out;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DispatchAddressResponseDto createDispatchAddress(DispatchAddressRequestDto req) {
        Map<String, Object> params = new HashMap<>();
        params.put("Name", req.getName());
        params.put("AddressLine1", req.getAddressLine1());
        params.put("AddressLine2", req.getAddressLine2());
        params.put("DispatchState", req.getDispatchState());
        params.put("Pincode", req.getPincode());
        params.put("CompanyId", cid());
        Integer id = repository.insertDispatchAddress(params);

        DispatchAddressResponseDto dto = new DispatchAddressResponseDto();
        dto.setDispatchAddressId(id);
        dto.setName(req.getName());
        dto.setAddressLine1(req.getAddressLine1());
        dto.setAddressLine2(req.getAddressLine2());
        dto.setDispatchState(req.getDispatchState());
        dto.setPincode(req.getPincode());
        return dto;
    }
}
