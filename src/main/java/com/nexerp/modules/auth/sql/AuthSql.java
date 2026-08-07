package com.nexerp.modules.auth.sql;

/**
 * PostgreSQL equivalents of the usp_Auth_* stored procedures.
 *
 * usp_Auth_GetUserPermissions used IF EXISTS(...)/ELSE to return one of two
 * different result sets from a single proc call. Postgres has no clean way
 * to conditionally shape a result set like that in one query, so it's split
 * into a small existence check (HAS_USER_ENTITLEMENTS) plus the two original
 * branches (GET_PERMISSIONS_USER_LEVEL / GET_PERMISSIONS_ROLE_LEVEL); the
 * repository picks one branch in Java, matching the original branching logic
 * exactly.
 */
public final class AuthSql {

    private AuthSql() {}

    public static final String GET_USER_BY_USERNAME = """
        SELECT
            u.user_id, u.username, u.password_hash, u.full_name, u.email,
            u.is_active, r.role_id, r.role_name, u.corporate_id, u.company_id
        FROM users u
        INNER JOIN roles r ON r.role_id = u.role_id
        WHERE u.username = :Username
        """;

    public static final String GET_USER_COMPANIES = """
        SELECT c.company_id, c.company_name, c.currency, c.gstin, c.is_active
        FROM companies c
        WHERE c.is_active = true
        ORDER BY c.company_name
        """;

    public static final String HAS_USER_ENTITLEMENTS = """
        SELECT 1 FROM user_entitlements WHERE user_id = :UserId LIMIT 1
        """;

    public static final String GET_PERMISSIONS_USER_LEVEL = """
        SELECT
            m.module_code, m.module_name, s.screen_code, s.screen_name,
            ue.can_create, ue.can_read, ue.can_update, ue.can_delete
        FROM user_entitlements ue
        JOIN screens s ON s.screen_id = ue.screen_id
        JOIN modules m ON m.module_id = s.module_id
        WHERE ue.user_id = :UserId AND ue.can_read = true
        ORDER BY m.sort_order, s.sort_order
        """;

    public static final String GET_PERMISSIONS_ROLE_LEVEL = """
        SELECT
            m.module_code, m.module_name, s.screen_code, s.screen_name,
            e.can_create, e.can_read, e.can_update, e.can_delete
        FROM users u
        JOIN role_entitlements e ON e.role_id = u.role_id
        JOIN screens s ON s.screen_id = e.screen_id
        JOIN modules m ON m.module_id = s.module_id
        WHERE u.user_id = :UserId AND e.can_read = true
        ORDER BY m.sort_order, s.sort_order
        """;

    public static final String UPDATE_LAST_LOGIN = """
        UPDATE users SET last_login = now(), updated_at = now()
        WHERE user_id = :UserId
        """;

    public static final String CHANGE_PASSWORD = """
        UPDATE users SET password_hash = :PasswordHash, updated_at = now()
        WHERE user_id = :UserId
        """;
}
