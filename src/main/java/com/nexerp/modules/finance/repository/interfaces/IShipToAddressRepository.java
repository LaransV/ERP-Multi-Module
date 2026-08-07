package com.nexerp.modules.finance.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IShipToAddressRepository {
    List<Map<String, Object>> getShipToAddresses(Integer companyId);
    Integer insertShipToAddress(Map<String, Object> params);
}
