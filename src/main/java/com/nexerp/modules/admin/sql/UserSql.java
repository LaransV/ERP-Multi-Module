package com.nexerp.modules.admin.sql;

/**
 * PostgreSQL equivalents of the usp_Admin_*User* stored procedures.
 * NOTE: pagination here is 0-based (:Page * :Size), unlike Finance's
 * 1-based ((:Page - 1) * :Size) - this matches the real proc exactly.
 */
public final class UserSql {

    private UserSql() {}

    public static final String GET_USERS = """
        SELECT
            u.user_id, u.username, u.email, u.full_name, u.phone,
            u.role_id, r.role_name,
            u.company_id, c.company_name,
            u.is_active, u.created_at, u.last_login,
            COUNT(*) OVER() AS "TotalCount"
        FROM users u
        LEFT JOIN roles r ON r.role_id = u.role_id
        LEFT JOIN companies c ON c.company_id = u.company_id
        WHERE (:Search = '' OR u.username ILIKE '%' || :Search || '%'
                            OR u.full_name ILIKE '%' || :Search || '%'
                            OR u.email ILIKE '%' || :Search || '%')
        ORDER BY u.full_name
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_USER_BY_ID = """
        SELECT
            u.user_id, u.username, u.email, u.full_name, u.phone,
            u.role_id, r.role_name,
            u.company_id, c.company_name,
            u.is_active, u.created_at, u.last_login
        FROM users u
        LEFT JOIN roles r ON r.role_id = u.role_id
        LEFT JOIN companies c ON c.company_id = u.company_id
        WHERE u.user_id = :UserId
        """;

    public static final String INSERT_USER = """
        INSERT INTO users
            (username, email, full_name, phone, role_id, company_id, password_hash, is_active)
        VALUES
            (:Username, :Email, :FullName, :Phone, :RoleId, :CompanyId, :PasswordHash, :IsActive)
        RETURNING user_id
        """;

    public static final String UPDATE_USER = """
        UPDATE users
        SET email = :Email,
            full_name = :FullName,
            phone = :Phone,
            role_id = :RoleId,
            company_id = :CompanyId,
            is_active = :IsActive,
            updated_at = now()
        WHERE user_id = :UserId
        """;

    public static final String DELETE_USER = """
        UPDATE users SET is_active = false, updated_at = now()
        WHERE user_id = :UserId
        """;

    public static final String TOGGLE_USER = """
        UPDATE users
        SET is_active = NOT is_active, updated_at = now()
        WHERE user_id = :UserId
        """;
}
