package com.nexerp.modules.inventory.mapper;

import com.nexerp.modules.inventory.dto.response.StockItemResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class StockMapper {

    public StockItemResponse toStock(Map<String, Object> r) {
        StockItemResponse s = new StockItemResponse();
        s.setStockId(r.get("stock_id") != null ? toInt(r.get("stock_id")) : 0); s.setProductId(toInt(r.get("product_id")));
        s.setProductName((String) r.get("product_name")); s.setProductCode((String) r.get("product_code")); s.setHsnCode((String) r.get("hsn_code")); s.setUnit((String) r.get("unit")); s.setCategoryName((String) r.get("category_name"));
        s.setOpeningStock(toBD(r.get("opening_stock"))); s.setCurrentStock(toBD(r.get("current_stock"))); s.setReservedStock(toBD(r.get("reserved_stock")));
        s.setAvailableStock(toBD(r.get("AvailableStock"))); s.setReorderLevel(toBD(r.get("reorder_level"))); s.setPurchasePrice(toBD(r.get("purchase_price"))); s.setStockValue(toBD(r.get("StockValue")));
        s.setLastUpdated(r.get("last_updated") != null ? r.get("last_updated").toString() : null);
        return s;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
