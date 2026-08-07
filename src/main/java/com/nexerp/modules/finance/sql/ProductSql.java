package com.nexerp.modules.finance.sql;

/**
 * PostgreSQL equivalents of the old MSSQL usp_Finance_*Product* stored procedures.
 */
public final class ProductSql {

    private ProductSql() {}

    public static final String GET_PRODUCTS = """
        SELECT p.*, COUNT(*) OVER() AS "TotalCount"
        FROM products p
        WHERE p.company_id = :CompanyId AND p.is_active = true
          AND (:Search = '' OR p.product_name ILIKE '%' || :Search || '%' OR p.product_code ILIKE '%' || :Search || '%')
        ORDER BY p.product_name
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String SEARCH_PRODUCTS = """
        SELECT p.product_id, p.product_name, p.product_code, p.unit, p.sale_price, p.tax_rate
        FROM products p
        WHERE p.company_id = :CompanyId AND p.is_active = true
          AND (p.product_name ILIKE '%' || :Search || '%' OR p.product_code ILIKE '%' || :Search || '%')
        ORDER BY p.product_name
        LIMIT 20
        """;

    public static final String GET_PRODUCT_BY_ID = """
        SELECT * FROM products WHERE product_id = :ProductId AND company_id = :CompanyId
        """;

    public static final String INSERT_PRODUCT = """
        INSERT INTO products
            (product_name, product_code, hsn_code, product_type, unit,
             tax_rate, purchase_price, sale_price, category_name, group_name,
             is_active, company_id)
        VALUES
            (:ProductName, NULLIF(:ProductCode, ''), NULLIF(:HsnCode, ''), :ProductType, NULLIF(:Unit, ''),
             :TaxRate, :PurchasePrice, :SalePrice, NULLIF(:CategoryName, ''), NULLIF(:GroupName, ''),
             true, :CompanyId)
        RETURNING product_id
        """;

    public static final String UPDATE_PRODUCT = """
        UPDATE products
        SET product_name = :ProductName, product_code = :ProductCode, hsn_code = :HsnCode,
            product_type = :ProductType, unit = :Unit, tax_rate = :TaxRate,
            purchase_price = :PurchasePrice, sale_price = :SalePrice,
            category_name = :CategoryName, group_name = :GroupName
        WHERE product_id = :ProductId AND company_id = :CompanyId
        """;

    public static final String DELETE_PRODUCT = """
        UPDATE products SET is_active = false
        WHERE product_id = :ProductId AND company_id = :CompanyId
        """;
}
