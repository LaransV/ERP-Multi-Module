package com.nexerp.modules.crm.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.crm.dto.request.LeadRequest;
import com.nexerp.modules.crm.dto.response.LeadListItem;
import com.nexerp.modules.crm.dto.response.LeadResponse;

import java.util.Map;

public interface ILeadService {
    PagedResponse<LeadListItem> listLeads(int page, int size, String status, String search);
    LeadResponse getLead(Integer id);
    LeadResponse createLead(LeadRequest req);
    LeadResponse updateLead(Integer id, LeadRequest req);
    void updateLeadStatus(Integer id, String status);
    void deleteLead(Integer id);
    Map<String, Integer> convertLead(Integer id);
}
