package com.nexerp.modules.crm.mapper;

import com.nexerp.modules.crm.dto.response.StatusWiseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CRMDashboardMapper {

    public StatusWiseDto toStatusWise(Map<String, Object> r) {
        StatusWiseDto sw = new StatusWiseDto();
        sw.setStatus((String) r.get("status"));
        sw.setCount(toLong(r.get("Count")));
        sw.setValue(toBD(r.get("Value")));
        return sw;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
