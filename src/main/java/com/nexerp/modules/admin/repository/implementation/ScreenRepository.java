package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.IScreenRepository;
import com.nexerp.modules.admin.sql.ScreenSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ScreenRepository implements IScreenRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getScreens(Integer moduleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("ModuleId", moduleId != null ? moduleId : 0);
        return jdbc.queryList(ScreenSql.GET_SCREENS, params);
    }
}
