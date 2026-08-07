package com.nexerp.modules.finance.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.finance.repository.interfaces.IDispatchAddressRepository;
import com.nexerp.modules.finance.sql.DispatchAddressSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DispatchAddressRepository implements IDispatchAddressRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getDispatchAddresses(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryList(DispatchAddressSql.GET_DISPATCH_ADDRESSES, params);
    }

    @Override
    public Integer insertDispatchAddress(Map<String, Object> params) {
        return jdbc.executeAndGetId(DispatchAddressSql.INSERT_DISPATCH_ADDRESS, params);
    }
}
