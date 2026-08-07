package com.nexerp.modules.crm.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface ILeadRepository {

    List<Map<String, Object>> getLeads(Map<String, Object> params);
    Map<String, Object> getLeadById(Integer id, Integer companyId);
    Integer insertLead(Map<String, Object> params);
    void updateLead(Map<String, Object> params);
    void updateLeadStatus(Integer id, String status, Integer companyId);
    void deleteLead(Integer id, Integer companyId);
    Map<String, Object> convertLeadToClient(Integer id, Integer companyId);
}
