package com.nexerp.modules.finance.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IDispatchAddressRepository {
    List<Map<String, Object>> getDispatchAddresses(Integer companyId);
    Integer insertDispatchAddress(Map<String, Object> params);
}
