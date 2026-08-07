package com.nexerp.modules.crm.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.crm.dto.request.ActivityRequest;
import com.nexerp.modules.crm.dto.response.ActivityResponse;

public interface IActivityService {
    PagedResponse<ActivityResponse> listActivities(int page, int size, Integer leadId);
    ActivityResponse createActivity(ActivityRequest req);
    ActivityResponse updateActivity(Integer id, ActivityRequest req);
    void deleteActivity(Integer id);
}
