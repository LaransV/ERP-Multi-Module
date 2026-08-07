package com.nexerp.modules.crm.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.crm.dto.request.FollowupRequest;
import com.nexerp.modules.crm.dto.response.FollowupResponse;

public interface IFollowupService {
    PagedResponse<FollowupResponse> listFollowups(int page, int size, Integer leadId, String status);
    FollowupResponse createFollowup(FollowupRequest req);
    FollowupResponse updateFollowup(Integer id, FollowupRequest req);
    void completeFollowup(Integer id, String notes);
    void deleteFollowup(Integer id);
}
