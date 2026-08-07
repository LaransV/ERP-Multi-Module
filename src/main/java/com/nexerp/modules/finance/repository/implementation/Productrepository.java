package com.nexerp.modules.finance.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.finance.repository.interfaces.IProductRepository;
import com.nexerp.modules.finance.sql.ProductSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class Productrepository implements IProductRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getProducts(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", (page - 1) * size);
        return jdbc.queryList(ProductSql.GET_PRODUCTS, params);
    }

    @Override
    public List<Map<String, Object>> searchProducts(Map<String, Object> params) {
        return jdbc.queryList(ProductSql.SEARCH_PRODUCTS, params);
    }

    @Override
    public Map<String, Object> getProductById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("ProductId", id);
        params.put("CompanyId", companyId);
        return jdbc.queryOne(ProductSql.GET_PRODUCT_BY_ID, params);
    }

    @Override
    public Integer insertProduct(Map<String, Object> params) {
        return jdbc.executeAndGetId(ProductSql.INSERT_PRODUCT, params);
    }

    @Override
    public void updateProduct(Map<String, Object> params) {
        jdbc.execute(ProductSql.UPDATE_PRODUCT, params);
    }

    @Override
    public void deleteProduct(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("ProductId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(ProductSql.DELETE_PRODUCT, params);
    }
}
