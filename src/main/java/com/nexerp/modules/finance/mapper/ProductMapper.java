package com.nexerp.modules.finance.mapper;

import com.nexerp.modules.finance.dto.request.ProductRequestDto;
import com.nexerp.modules.finance.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductMapper {

    // ── Products ─────────────────────────────
    public Map<String, Object> toProductParams(ProductRequestDto r) {
        Map<String, Object> p = new HashMap<>();
        p.put("ProductName",   r.getProductName());
        p.put("ProductCode",   nvl(r.getProductCode()));
        p.put("HsnCode",       nvl(r.getHsnCode()));
        p.put("ProductType",   r.getProductType() != null ? r.getProductType() : "GOODS");
        p.put("Unit",          nvl(r.getUnit()));
        p.put("TaxRate",       r.getTaxRate() != null ? r.getTaxRate() : BigDecimal.ZERO);
        p.put("PurchasePrice", r.getPurchasePrice() != null ? r.getPurchasePrice() : BigDecimal.ZERO);
        p.put("SalePrice",     r.getSalePrice() != null ? r.getSalePrice() : BigDecimal.ZERO);
        p.put("CategoryName",  nvl(r.getCategoryName()));
        p.put("GroupName",     nvl(r.getGroupName()));
        return p;
    }

    public ProductResponseDto toProduct(Map<String, Object> r) {
        ProductResponseDto p = new ProductResponseDto();
        p.setProductId(toInt(r.get("product_id")));
        p.setProductName((String) r.get("product_name"));
        p.setProductCode((String) r.get("product_code"));
        p.setHsnCode((String) r.get("hsn_code"));
        p.setProductType((String) r.get("product_type"));
        p.setUnit((String) r.get("unit"));
        p.setTaxRate(toBD(r.get("tax_rate")));
        p.setPurchasePrice(toBD(r.get("purchase_price")));
        p.setSalePrice(toBD(r.get("sale_price")));
        p.setCategoryName((String) r.get("category_name"));
        p.setGroupName((String) r.get("group_name"));
        p.setActive(Boolean.TRUE.equals(r.get("is_active")));
        p.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return p;
    }

    public String     nvl(String s)   { return s != null ? s : ""; }
    public int        toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long        toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o)  { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
    public long firstLong(List<Map<String, Object>> rows, String key, long dflt) {
        if (rows.isEmpty()) return dflt;
        Object v = rows.get(0).get(key);
        if (v instanceof Number) return ((Number) v).longValue();
        return dflt;
    }
}
