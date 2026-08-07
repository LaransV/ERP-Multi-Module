package com.nexerp.modules.inventory.mapper;

import com.nexerp.modules.inventory.dto.response.MovementResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class MovementMapper {

    public MovementResponse toMovement(Map<String, Object> r) {
        MovementResponse m = new MovementResponse();
        m.setMovementId(toInt(r.get("movement_id"))); m.setProductId(toInt(r.get("product_id"))); m.setProductName((String) r.get("product_name")); m.setMovementType((String) r.get("movement_type"));
        m.setQuantity(toBD(r.get("quantity"))); m.setReferenceType((String) r.get("reference_type")); m.setReferenceNumber((String) r.get("reference_number"));
        m.setNotes((String) r.get("notes")); m.setCreatedBy((String) r.get("created_by")); m.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return m;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
