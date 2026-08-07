package com.nexerp.modules.inventory.mapper;

import com.nexerp.modules.inventory.dto.response.PoItemResponse;
import com.nexerp.modules.inventory.dto.response.PoListItem;
import com.nexerp.modules.inventory.dto.response.PoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderMapper {

    public PoListItem toPoListItem(Map<String, Object> r) {
        PoListItem p = new PoListItem();
        p.setPoId(toInt(r.get("po_id"))); p.setPoNumber((String) r.get("po_number")); p.setVendorName((String) r.get("vendor_name"));
        p.setPoDate(r.get("po_date") != null ? r.get("po_date").toString() : null); p.setStatus((String) r.get("status"));
        p.setTotalAmount(toBD(r.get("total_amount"))); p.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return p;
    }

    public PoResponse toPO(Map<String, Object> h, List<Map<String, Object>> itemRows) {
        PoResponse p = new PoResponse();
        p.setPoId(toInt(h.get("po_id"))); p.setPoNumber((String) h.get("po_number")); p.setVendorId(toInt(h.get("vendor_id"))); p.setVendorName((String) h.get("vendor_name"));
        p.setPoDate(h.get("po_date") != null ? h.get("po_date").toString() : null); p.setExpectedDate(h.get("expected_date") != null ? h.get("expected_date").toString() : null);
        p.setStatus((String) h.get("status")); p.setTotalAmount(toBD(h.get("total_amount"))); p.setNotes((String) h.get("notes")); p.setCreatedAt(h.get("created_at") != null ? h.get("created_at").toString() : null);
        p.setItems(itemRows.stream().map(ir -> {
            PoItemResponse item = new PoItemResponse();
            item.setProductId(toInt(ir.get("product_id"))); item.setProductName((String) ir.get("product_name"));
            item.setQuantity(toBD(ir.get("quantity"))); item.setUnitPrice(toBD(ir.get("unit_price"))); item.setTotalAmount(toBD(ir.get("total_amount")));
            return item;
        }).collect(Collectors.toList()));
        return p;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
