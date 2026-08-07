package com.nexerp.modules.admin.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.admin.repository.interfaces.IModuleRepository;
import com.nexerp.modules.admin.sql.ScreenSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ModuleRepository implements IModuleRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getModules() {
        return jdbc.queryList(ScreenSql.GET_MODULES, new HashMap<>());
    }
}
