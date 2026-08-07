package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.CompanyResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CompanyMapper {

    public CompanyResponse toCompany(Map<String, Object> r) {
        CompanyResponse c = new CompanyResponse();
        c.setCompanyId(toInt(r.get("company_id")));
        c.setCompanyName((String) r.get("company_name"));
        c.setCurrency((String) r.get("currency"));
        c.setGstin((String) r.get("gstin"));
        c.setAddress((String) r.get("address"));
        c.setPhone((String) r.get("phone"));
        c.setEmail((String) r.get("email"));
        c.setActive(Boolean.TRUE.equals(r.get("is_active")));
        c.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return c;
    }

    public boolean toBool(Object val) {
        if (val == null)            return false;
        if (val instanceof Boolean) return (Boolean) val;
        if (val instanceof Number)  return ((Number) val).intValue() != 0;
        return false;
    }

    public int  toInt(Object o)  { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long toLong(Object o) { if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
}
