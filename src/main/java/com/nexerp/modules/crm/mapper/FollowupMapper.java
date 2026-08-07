package com.nexerp.modules.crm.mapper;

import com.nexerp.modules.crm.dto.response.FollowupResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class FollowupMapper {

    public FollowupResponse toFollowup(Map<String, Object> r) {
        FollowupResponse f = new FollowupResponse();
        if (r.get("followup_id") != null) f.setFollowupId(toInt(r.get("followup_id")));
        if (r.get("lead_id") != null) f.setLeadId(toInt(r.get("lead_id")));
        f.setLeadName((String) r.get("lead_name"));
        f.setScheduledAt(r.get("scheduled_at") != null ? r.get("scheduled_at").toString() : null);
        f.setCompletedAt(r.get("completed_at") != null ? r.get("completed_at").toString() : null);
        f.setNotes((String) r.get("notes"));
        f.setFollowupType((String) r.get("followup_type"));
        f.setStatus((String) r.get("status"));
        f.setCreatedBy((String) r.get("created_by"));
        return f;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
