package com.nexerp.modules.crm.mapper;

import com.nexerp.modules.crm.dto.response.ActivityResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ActivityMapper {

    public ActivityResponse toActivity(Map<String, Object> r) {
        ActivityResponse a = new ActivityResponse();
        if (r.get("activity_id") != null) a.setActivityId(toInt(r.get("activity_id")));
        if (r.get("lead_id") != null) a.setLeadId(toInt(r.get("lead_id")));
        if (r.get("client_id") != null) a.setClientId(toInt(r.get("client_id")));
        a.setEntityName((String) r.get("entity_name")); a.setActivityType((String) r.get("activity_type")); a.setTitle((String) r.get("title")); a.setDescription((String) r.get("description"));
        a.setScheduledAt(r.get("scheduled_at") != null ? r.get("scheduled_at").toString() : null); a.setCompletedAt(r.get("completed_at") != null ? r.get("completed_at").toString() : null);
        a.setStatus((String) r.get("status")); a.setCreatedBy((String) r.get("created_by")); a.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return a;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
