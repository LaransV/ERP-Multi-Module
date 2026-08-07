package com.nexerp.modules.admin.sql;

/** PostgreSQL equivalents of the usp_Admin_*Role* stored procedures. */
public final class RoleSql {

    private RoleSql() {}

    public static final String GET_ROLES = """
        SELECT role_id, role_name, role_description, is_active, created_at
        FROM roles
        ORDER BY role_id
        """;

    public static final String GET_ROLE_BY_ID = """
        SELECT role_id, role_name, role_description, is_active, created_at
        FROM roles
        WHERE role_id = :RoleId
        """;

    public static final String INSERT_ROLE = """
        INSERT INTO roles (role_name, role_description, is_active)
        VALUES (:RoleName, :RoleDescription, :IsActive)
        RETURNING role_id
        """;

    public static final String UPDATE_ROLE = """
        UPDATE roles
        SET role_name = :RoleName,
            role_description = :RoleDescription,
            is_active = :IsActive,
            updated_at = now()
        WHERE role_id = :RoleId
        """;

    // Original proc deletes RoleEntitlements first, then the Role itself,
    // both in one call - reproduced as two statements run together by the
    // repository inside a @Transactional service method.
    public static final String DELETE_ROLE_ENTITLEMENTS = """
        DELETE FROM role_entitlements WHERE role_id = :RoleId
        """;

    public static final String DELETE_ROLE = """
        DELETE FROM roles WHERE role_id = :RoleId
        """;
}
