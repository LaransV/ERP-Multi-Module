package com.nexerp.modules.crm.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.crm.repository.interfaces.IFollowupRepository;
import com.nexerp.modules.crm.sql.FollowupSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FollowupRepository implements IFollowupRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getFollowups(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(FollowupSql.GET_FOLLOWUPS, params);
    }

    @Override
    public Map<String, Object> getFollowupById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("FollowupId", id);
        return jdbc.queryOne(FollowupSql.GET_FOLLOWUP_BY_ID, params);
    }

    @Override
    public Integer insertFollowup(Map<String, Object> params) {
        return jdbc.executeAndGetId(FollowupSql.INSERT_FOLLOWUP, params);
    }

    @Override
    public void updateFollowup(Map<String, Object> params) {
        jdbc.execute(FollowupSql.UPDATE_FOLLOWUP, params);
    }

    @Override
    public void completeFollowup(Integer id, String notes, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("FollowupId", id);
        params.put("Notes", notes);
        params.put("CompanyId", companyId);
        jdbc.execute(FollowupSql.COMPLETE_FOLLOWUP, params);
    }

    @Override
    public void deleteFollowup(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("FollowupId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(FollowupSql.DELETE_FOLLOWUP, params);
    }

    @Override
    public List<Map<String, Object>> getRecentFollowups(int limit, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("Limit", limit);
        params.put("CompanyId", companyId);
        return jdbc.queryList(FollowupSql.GET_RECENT_FOLLOWUPS, params);
    }
}
