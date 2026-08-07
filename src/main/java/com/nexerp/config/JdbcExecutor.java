package com.nexerp.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Generic PostgreSQL query executor used by all module repositories.
 *
 * Replaces the old SpExecutor (which called MSSQL stored procedures via
 * SimpleJdbcCall). Every module now keeps its own SQL in a "sql" package
 * (e.g. modules/finance/sql/ClientSql.java) as plain named-parameter SQL
 * strings, and repositories call this executor to run them.
 *
 * INSERT statements are expected to end with a `RETURNING "XxxId"` clause
 * so executeAndGetId can read the generated id directly from PostgreSQL,
 * instead of relying on JDBC generated-key metadata (which behaves
 * inconsistently across drivers).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JdbcExecutor {

    private final NamedParameterJdbcTemplate jdbc;

    /** Runs a SELECT and returns every row as a case-preserving Map. */
    public List<Map<String, Object>> queryList(String sql, Map<String, Object> params) {
        try {
            return jdbc.queryForList(sql, params);
        } catch (Exception e) {
            log.error("queryList failed | sql={}", sql, e);
            throw e;
        }
    }

    /** Runs a SELECT expected to return 0 or 1 row. */
    public Map<String, Object> queryOne(String sql, Map<String, Object> params) {
        List<Map<String, Object>> rows = queryList(sql, params);
        return rows.isEmpty() ? null : rows.get(0);
    }

    /** Runs an INSERT/UPDATE/DELETE. Returns the number of affected rows. */
    public int execute(String sql, Map<String, Object> params) {
        try {
            return jdbc.update(sql, new MapSqlParameterSource(params));
        } catch (Exception e) {
            log.error("execute failed | sql={}", sql, e);
            throw e;
        }
    }

    /**
     * Runs an INSERT that ends with `RETURNING "IdColumn"` and returns the
     * generated id.
     */
    public Integer executeAndGetId(String sql, Map<String, Object> params) {
        try {
            return jdbc.queryForObject(sql, new MapSqlParameterSource(params), Integer.class);
        } catch (Exception e) {
            log.error("executeAndGetId failed | sql={}", sql, e);
            throw e;
        }
    }
}
