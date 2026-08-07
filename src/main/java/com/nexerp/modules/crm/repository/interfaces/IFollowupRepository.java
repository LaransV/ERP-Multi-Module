package com.nexerp.modules.crm.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IFollowupRepository {

    List<Map<String, Object>> getFollowups(Map<String, Object> params);
    Map<String, Object> getFollowupById(Integer id);
    Integer insertFollowup(Map<String, Object> params);
    void updateFollowup(Map<String, Object> params);
    void completeFollowup(Integer id, String notes, Integer companyId);
    void deleteFollowup(Integer id, Integer companyId);
    List<Map<String, Object>> getRecentFollowups(int limit, Integer companyId);
}
