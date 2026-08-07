package com.nexerp.modules.crm.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.crm.repository.interfaces.ILeadRepository;
import com.nexerp.modules.crm.sql.LeadSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LeadRepository implements ILeadRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getLeads(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(LeadSql.GET_LEADS, params);
    }

    @Override
    public Map<String, Object> getLeadById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("LeadId", id);
        params.put("CompanyId", companyId);
        return jdbc.queryOne(LeadSql.GET_LEAD_BY_ID, params);
    }

    @Override
    public Integer insertLead(Map<String, Object> params) {
        return jdbc.executeAndGetId(LeadSql.INSERT_LEAD, params);
    }

    @Override
    public void updateLead(Map<String, Object> params) {
        jdbc.execute(LeadSql.UPDATE_LEAD, params);
    }

    @Override
    public void updateLeadStatus(Integer id, String status, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("LeadId", id);
        params.put("Status", status);
        params.put("CompanyId", companyId);
        jdbc.execute(LeadSql.UPDATE_LEAD_STATUS, params);
    }

    @Override
    public void deleteLead(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("LeadId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(LeadSql.DELETE_LEAD, params);
    }

    /**
     * usp_CRM_ConvertLeadToClient did 3 steps in one proc call: read the
     * lead's core fields, insert a new Client from them, mark the lead WON
     * with the new ClientId. Split into 3 statements here - stays atomic
     * because this only runs inside a @Transactional service method.
     */
    @Override
    public Map<String, Object> convertLeadToClient(Integer id, Integer companyId) {
        Map<String, Object> leadParams = new HashMap<>();
        leadParams.put("LeadId", id);
        leadParams.put("CompanyId", companyId);

        Map<String, Object> lead = jdbc.queryOne(LeadSql.GET_LEAD_CORE_FIELDS, leadParams);

        Map<String, Object> clientParams = new HashMap<>();
        clientParams.put("LeadName", lead.get("lead_name"));
        clientParams.put("Email", lead.get("email"));
        clientParams.put("Phone", lead.get("phone"));
        clientParams.put("CompanyId", companyId);
        Integer clientId = jdbc.executeAndGetId(LeadSql.INSERT_CLIENT_FROM_LEAD, clientParams);

        Map<String, Object> updateParams = new HashMap<>();
        updateParams.put("LeadId", id);
        updateParams.put("CompanyId", companyId);
        updateParams.put("ClientId", clientId);
        jdbc.execute(LeadSql.MARK_LEAD_WON, updateParams);

        Map<String, Object> result = new HashMap<>();
        result.put("ClientId", clientId);
        return result;
    }
}
