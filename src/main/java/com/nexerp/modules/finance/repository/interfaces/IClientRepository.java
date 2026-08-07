package com.nexerp.modules.finance.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IClientRepository {

    // ── Clients ──────────────────────────────
    List<Map<String, Object>> getClients(Map<String, Object> params);
    List<Map<String, Object>> searchClients(Map<String, Object> params);
    Map<String, Object> getClientById(Integer id, Integer companyId);
    Integer insertClient(Map<String, Object> params);
    void updateClient(Map<String, Object> params);
    void deleteClient(Integer id, Integer companyId);
}
