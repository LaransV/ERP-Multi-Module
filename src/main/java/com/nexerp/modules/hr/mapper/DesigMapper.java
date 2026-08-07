package com.nexerp.modules.hr.mapper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DesigMapper {

    public String nvl(String s)   { return s != null ? s : ""; }
    public String str(Object o)   { return o != null ? o.toString() : null; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
