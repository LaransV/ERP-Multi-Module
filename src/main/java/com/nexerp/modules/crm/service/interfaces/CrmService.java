package com.nexerp.modules.crm.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.crm.dto.request.ActivityRequest;
import com.nexerp.modules.crm.dto.request.FollowupRequest;
import com.nexerp.modules.crm.dto.request.LeadRequest;
import com.nexerp.modules.crm.dto.response.ActivityResponse;
import com.nexerp.modules.crm.dto.response.CRMDashboardResponse;
import com.nexerp.modules.crm.dto.response.FollowupResponse;
import com.nexerp.modules.crm.dto.response.LeadListItem;
import com.nexerp.modules.crm.dto.response.LeadResponse;

import java.util.Map;

public interface CrmService {

    PagedResponse<LeadListItem> listLeads(int page, int size, String status, String search);
    LeadResponse getLead(Integer id);
    LeadResponse createLead(LeadRequest req);
    LeadResponse updateLead(Integer id, LeadRequest req);
    void updateLeadStatus(Integer id, String status);
    void deleteLead(Integer id);
    Map<String, Integer> convertLead(Integer id);

    PagedResponse<FollowupResponse> listFollowups(int page, int size, Integer leadId, String status);
    FollowupResponse createFollowup(FollowupRequest req);
    FollowupResponse updateFollowup(Integer id, FollowupRequest req);
    void completeFollowup(Integer id, String notes);
    void deleteFollowup(Integer id);

    PagedResponse<ActivityResponse> listActivities(int page, int size, Integer leadId);
    ActivityResponse createActivity(ActivityRequest req);
    ActivityResponse updateActivity(Integer id, ActivityRequest req);
    void deleteActivity(Integer id);

    CRMDashboardResponse getDashboard();
}
