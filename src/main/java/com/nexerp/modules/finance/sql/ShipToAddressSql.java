package com.nexerp.modules.finance.sql;

/** SQL for the ShipToAddresses master table (reusable ship-to addresses). */
public final class ShipToAddressSql {

    private ShipToAddressSql() {}

    public static final String GET_SHIP_TO_ADDRESSES = """
        SELECT ship_to_address_id, name, address_line1, address_line2, shipping_state, pincode, gstin
        FROM ship_to_addresses
        WHERE company_id = :CompanyId AND is_active = true
        ORDER BY name
        """;

    public static final String INSERT_SHIP_TO_ADDRESS = """
        INSERT INTO ship_to_addresses (name, address_line1, address_line2, shipping_state, pincode, gstin, is_active, company_id)
        VALUES (:Name, :AddressLine1, :AddressLine2, :ShippingState, :Pincode, :Gstin, true, :CompanyId)
        RETURNING ship_to_address_id
        """;
}
