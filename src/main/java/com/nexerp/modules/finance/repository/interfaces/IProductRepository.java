package com.nexerp.modules.finance.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IProductRepository {
    // ── Products ─────────────────────────────
    List<Map<String, Object>> getProducts(Map<String, Object> params);
    List<Map<String, Object>> searchProducts(Map<String, Object> params);
    Map<String, Object> getProductById(Integer id, Integer companyId);
    Integer insertProduct(Map<String, Object> params);
    void updateProduct(Map<String, Object> params);
    void deleteProduct(Integer id, Integer companyId);
}
