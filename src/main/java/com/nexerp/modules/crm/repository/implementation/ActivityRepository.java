package com.nexerp.modules.crm.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.crm.repository.interfaces.IActivityRepository;
import com.nexerp.modules.crm.sql.ActivitySql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ActivityRepository implements IActivityRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getActivities(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(ActivitySql.GET_ACTIVITIES, params);
    }

    @Override
    public Map<String, Object> getActivityById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("ActivityId", id);
        return jdbc.queryOne(ActivitySql.GET_ACTIVITY_BY_ID, params);
    }

    @Override
    public Integer insertActivity(Map<String, Object> params) {
        return jdbc.executeAndGetId(ActivitySql.INSERT_ACTIVITY, params);
    }

    @Override
    public void updateActivity(Map<String, Object> params) {
        jdbc.execute(ActivitySql.UPDATE_ACTIVITY, params);
    }

    @Override
    public void deleteActivity(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("ActivityId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(ActivitySql.DELETE_ACTIVITY, params);
    }
}
