package com.nexerp.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Types;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpExecutor {

    private final DataSource   dataSource;
    private final JdbcTemplate jdbc;

    @PostConstruct
    public void checkConnection() {
        try (Connection conn = dataSource.getConnection()) {
            log.info("==============================================");
            log.info("✅ DATABASE CONNECTED SUCCESSFULLY");
            log.info("   DB URL      : {}", conn.getMetaData().getURL());
            log.info("   DB Name     : {}", conn.getCatalog());
            log.info("   DB User     : {}", conn.getMetaData().getUserName());
            log.info("   Driver      : {}", conn.getMetaData().getDriverName());
            log.info("==============================================");
        } catch (Exception e) {
            log.error("==============================================");
            log.error("❌ DATABASE CONNECTION FAILED");
            log.error("   Error : {}", e.getMessage());
            log.error("==============================================");
        }
    }
    public List<Map<String, Object>> queryList(String spName, Map<String, Object> params) {
        log.debug("SP queryList: {}", spName);
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                    .withProcedureName(spName)
                    .returningResultSet("results", (rs, rowNum) -> {
                        Map<String, Object> row = new LinkedHashMap<>();
                        int cols = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= cols; i++) {
                            row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                        }
                        return row;
                    });
            Map<String, Object> in = params != null ? params : new HashMap<>();
            Map<String, Object> out = call.execute(in);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> results =
                    (List<Map<String, Object>>) out.get("results");
            return results != null ? results : new ArrayList<>();
        } catch (Exception e) {
            log.error("SP queryList failed: {}", spName, e);
            throw e;
        }
    }

    public Map<String, Object> queryOne(String spName, Map<String, Object> params) {
        List<Map<String, Object>> list = queryList(spName, params);
        return list.isEmpty() ? null : list.get(0);
    }

    public Map<String, Object> execute(String spName, Map<String, Object> params) {
        log.debug("SP execute: {}", spName);
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                    .withProcedureName(spName);
            Map<String, Object> in = params != null ? params : new HashMap<>();
            return call.execute(in);
        } catch (Exception e) {
            log.error("SP execute failed: {}", spName, e);
            throw e;
        }
    }

    public Integer executeAndGetId(String spName, Map<String, Object> params) {
        log.debug("SP executeAndGetId: {}", spName);
        try {
            SimpleJdbcCall call = new SimpleJdbcCall(dataSource)
                    .withProcedureName(spName)
                    .returningResultSet("result", (rs, rowNum) -> null);
            Map<String, Object> in = params != null ? params : new HashMap<>();
            Map<String, Object> out = call.execute(in);
            return (Integer) out.get("NewId");
        } catch (Exception e) {
            log.error("SP executeAndGetId failed: {}", spName, e);
            throw e;
        }
    }
}