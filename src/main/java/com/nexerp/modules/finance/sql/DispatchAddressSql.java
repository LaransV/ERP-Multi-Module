package com.nexerp.modules.finance.sql;

/** SQL for the DispatchAddresses master table (reusable dispatch-from addresses). */
public final class DispatchAddressSql {

    private DispatchAddressSql() {}

    public static final String GET_DISPATCH_ADDRESSES = """
        SELECT dispatch_address_id, name, address_line1, address_line2, dispatch_state, pincode
        FROM dispatch_addresses
        WHERE company_id = :CompanyId AND is_active = true
        ORDER BY name
        """;

    public static final String INSERT_DISPATCH_ADDRESS = """
        INSERT INTO dispatch_addresses (name, address_line1, address_line2, dispatch_state, pincode, is_active, company_id)
        VALUES (:Name, :AddressLine1, :AddressLine2, :DispatchState, :Pincode, true, :CompanyId)
        RETURNING dispatch_address_id
        """;
}
