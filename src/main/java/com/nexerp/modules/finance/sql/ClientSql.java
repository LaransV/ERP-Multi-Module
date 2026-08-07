package com.nexerp.modules.finance.sql;

/**
 * PostgreSQL equivalents of the MSSQL usp_Finance_*Client* stored procedures.
 * Verified against the live database backup (NexERP_full_backup.sql, 2026-07-20) -
 * every column and join here matches the real proc definitions, not an older
 * cached copy.
 */
public final class ClientSql {

    private ClientSql() {}

    public static final String GET_CLIENTS = """
        SELECT c.*, COUNT(*) OVER() AS "TotalCount"
        FROM clients c
        WHERE c.company_id = :CompanyId
          AND c.is_active = true
          AND (:Search = '' OR c.client_name ILIKE '%' || :Search || '%' OR c.email ILIKE '%' || :Search || '%')
          AND (:StateName = '' OR c.state ILIKE '%' || :StateName || '%')
        ORDER BY c.client_name
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    /** Mirrors usp_Finance_GetClientById exactly: joins Users twice to resolve
     * CreatedBy/ModifiedBy (stored on Clients as UserId ints) into usernames.
     * The proc SELECTs c.* first, then re-aliases cu.Username AS CreatedBy and
     * mu.Username AS ModifiedBy - i.e. it deliberately shadows the raw integer
     * columns with the resolved username strings in the result set, and
     * ClientMapper.toClient() reads CreatedBy/ModifiedBy expecting strings.
     * Since Spring's row-to-map mapping keeps the LAST column with a given
     * label, listing the aliases after c.* reproduces that exact behavior. */
    public static final String GET_CLIENT_BY_ID = """
        SELECT
            c.*,
            cu.username AS created_by,
            c.created_at AS "CreatedDate",
            mu.username AS modified_by,
            c.updated_at AS "ModifiedDate"
        FROM clients c
        LEFT JOIN users cu ON cu.user_id = c.created_by
        LEFT JOIN users mu ON mu.user_id = c.modified_by
        WHERE c.company_id = :CompanyId AND c.client_id = :ClientId
          AND c.is_active = true
        ORDER BY c.client_id DESC
        """;

    public static final String SEARCH_CLIENTS = """
        SELECT c.client_id, c.client_name, c.email, c.phone, c.gst_number, c.payment_terms
        FROM clients c
        WHERE c.company_id = :CompanyId
          AND c.is_active = true
          AND (c.client_name ILIKE '%' || :Query || '%' OR c.email ILIKE '%' || :Query || '%')
        ORDER BY c.client_name
        LIMIT 20
        """;

    /** Sets both CreatedBy and ModifiedBy to the acting user's id on insert,
     * matching usp_Finance_InsertClient exactly. */
    public static final String INSERT_CLIENT = """
        INSERT INTO clients
            (client_name, alias_name, vendor_code, email, website,
             landline_phone, phone, gstn_type, gst_number, pan_number,
             address, address_line2, city, country, state, pincode,
             currency, payment_terms, is_active, company_id,
             created_by, modified_by)
        VALUES
            (:ClientName, :AliasName, :VendorCode, :Email, :Website,
             :LandlinePhone, :Phone, :GstnType, :GstNumber, :PanNumber,
             :Address, :AddressLine2, :City, :Country, :State, :Pincode,
             :Currency, :PaymentTerms, true, :CompanyId,
             :UserId, :UserId)
        RETURNING client_id
        """;

    public static final String UPDATE_CLIENT = """
        UPDATE clients
        SET client_name = :ClientName,
            alias_name = :AliasName,
            vendor_code = :VendorCode,
            email = :Email,
            website = :Website,
            landline_phone = :LandlinePhone,
            phone = :Phone,
            gstn_type = :GstnType,
            gst_number = :GstNumber,
            pan_number = :PanNumber,
            address = :Address,
            address_line2 = :AddressLine2,
            city = :City,
            country = :Country,
            state = :State,
            pincode = :Pincode,
            currency = :Currency,
            payment_terms = :PaymentTerms,
            modified_by = :UserId,
            updated_at = now()
        WHERE client_id = :ClientId AND company_id = :CompanyId
        """;

    public static final String DELETE_CLIENT = """
        UPDATE clients SET is_active = false
        WHERE client_id = :ClientId AND company_id = :CompanyId
        """;
}
