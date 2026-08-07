package com.nexerp.modules.crm.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IActivityRepository {

    List<Map<String, Object>> getActivities(Map<String, Object> params);
    Map<String, Object> getActivityById(Integer id);
    Integer insertActivity(Map<String, Object> params);
    void updateActivity(Map<String, Object> params);
    void deleteActivity(Integer id, Integer companyId);
}
