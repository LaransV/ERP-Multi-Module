package com.nexerp.modules.hr.mapper;

import com.nexerp.modules.hr.dto.response.DeptCountDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class HRMSDashboardMapper {

    public DeptCountDto toDeptCount(Map<String, Object> r) {
        DeptCountDto d = new DeptCountDto();
        d.setDeptName((String) r.get("dept_name"));
        d.setCount(toInt(r.get("Count")));
        return d;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public String str(Object o)   { return o != null ? o.toString() : null; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
