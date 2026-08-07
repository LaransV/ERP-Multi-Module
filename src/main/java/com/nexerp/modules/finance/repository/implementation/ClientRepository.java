package com.nexerp.modules.finance.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.finance.repository.interfaces.IClientRepository;
import com.nexerp.modules.finance.sql.ClientSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ClientRepository implements IClientRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getClients(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", (page - 1) * size);
        return jdbc.queryList(ClientSql.GET_CLIENTS, params);
    }

    @Override
    public List<Map<String, Object>> searchClients(Map<String, Object> params) {
        return jdbc.queryList(ClientSql.SEARCH_CLIENTS, params);
    }

    @Override
    public Map<String, Object> getClientById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("ClientId", id);
        params.put("CompanyId", companyId);
        return jdbc.queryOne(ClientSql.GET_CLIENT_BY_ID, params);
    }

    @Override
    public Integer insertClient(Map<String, Object> params) {
        return jdbc.executeAndGetId(ClientSql.INSERT_CLIENT, params);
    }

    @Override
    public void updateClient(Map<String, Object> params) {
        jdbc.execute(ClientSql.UPDATE_CLIENT, params);
    }

    @Override
    public void deleteClient(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("ClientId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(ClientSql.DELETE_CLIENT, params);
    }
}
