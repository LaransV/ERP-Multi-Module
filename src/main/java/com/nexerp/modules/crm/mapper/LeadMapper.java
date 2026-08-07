package com.nexerp.modules.crm.mapper;

import com.nexerp.modules.crm.dto.request.LeadRequest;
import com.nexerp.modules.crm.dto.response.LeadListItem;
import com.nexerp.modules.crm.dto.response.LeadResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class LeadMapper {

    public Map<String, Object> toLeadParams(LeadRequest r) {
        Map<String, Object> p = new HashMap<>();
        p.put("LeadName", r.getLeadName()); p.put("Company", nvl(r.getCompany())); p.put("Email", nvl(r.getEmail())); p.put("Phone", r.getPhone());
        p.put("Source", nvl(r.getSource())); p.put("Status", r.getStatus() != null ? r.getStatus() : "NEW"); p.put("Priority", r.getPriority() != null ? r.getPriority() : "MEDIUM");
        p.put("AssignedToId", r.getAssignedToId() != null ? r.getAssignedToId() : 0); p.put("ExpectedValue", r.getExpectedValue() != null ? r.getExpectedValue() : BigDecimal.ZERO);
        p.put("ExpectedCloseDate", nvl(r.getExpectedCloseDate())); p.put("Notes", nvl(r.getNotes())); p.put("RejectedReason", nvl(r.getRejectedReason()));
        return p;
    }

    public LeadListItem toLeadListItem(Map<String, Object> r) {
        LeadListItem l = new LeadListItem();
        l.setLeadId(toInt(r.get("lead_id"))); l.setLeadName((String) r.get("lead_name")); l.setCompany((String) r.get("company")); l.setPhone((String) r.get("phone"));
        l.setSource((String) r.get("source")); l.setStatus((String) r.get("status")); l.setPriority((String) r.get("priority")); l.setAssignedToName((String) r.get("AssignedToName"));
        l.setExpectedValue(toBD(r.get("expected_value"))); l.setNextFollowupDate(r.get("next_followup_date") != null ? r.get("next_followup_date").toString() : null);
        l.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return l;
    }

    public LeadResponse toLead(Map<String, Object> r) {
        LeadResponse l = new LeadResponse();
        l.setLeadId(toInt(r.get("lead_id"))); l.setLeadName((String) r.get("lead_name")); l.setCompany((String) r.get("company")); l.setEmail((String) r.get("email")); l.setPhone((String) r.get("phone"));
        l.setSource((String) r.get("source")); l.setStatus((String) r.get("status")); l.setPriority((String) r.get("priority"));
        Object assignedId = r.get("assigned_to_id"); l.setAssignedToId(assignedId instanceof Number ? ((Number) assignedId).intValue() : null); l.setAssignedToName((String) r.get("AssignedToName"));
        l.setExpectedValue(toBD(r.get("expected_value"))); l.setExpectedCloseDate(r.get("expected_close_date") != null ? r.get("expected_close_date").toString() : null);
        l.setNotes((String) r.get("notes")); l.setRejectedReason((String) r.get("rejected_reason")); l.setNextFollowupDate(r.get("next_followup_date") != null ? r.get("next_followup_date").toString() : null);
        l.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return l;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
