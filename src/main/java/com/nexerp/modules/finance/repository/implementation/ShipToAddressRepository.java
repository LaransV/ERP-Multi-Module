package com.nexerp.modules.finance.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.finance.repository.interfaces.IShipToAddressRepository;
import com.nexerp.modules.finance.sql.ShipToAddressSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ShipToAddressRepository implements IShipToAddressRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getShipToAddresses(Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("CompanyId", companyId);
        return jdbc.queryList(ShipToAddressSql.GET_SHIP_TO_ADDRESSES, params);
    }

    @Override
    public Integer insertShipToAddress(Map<String, Object> params) {
        return jdbc.executeAndGetId(ShipToAddressSql.INSERT_SHIP_TO_ADDRESS, params);
    }
}
