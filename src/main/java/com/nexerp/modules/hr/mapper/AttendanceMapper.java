package com.nexerp.modules.hr.mapper;

import com.nexerp.modules.hr.dto.request.AttendanceRequest;
import com.nexerp.modules.hr.dto.response.AttendanceResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class AttendanceMapper {

    public Map<String, Object> toAttendanceParams(AttendanceRequest r) {
        Map<String, Object> p = new HashMap<>();
        p.put("EmpId",          r.getEmpId());
        p.put("AttendanceDate", r.getAttendanceDate());
        p.put("CheckIn",        nvl(r.getCheckIn()));
        p.put("CheckOut",       nvl(r.getCheckOut()));
        p.put("Status",         nvl(r.getStatus()));
        p.put("Source",         nvl(r.getSource()));
        p.put("Notes",          nvl(r.getNotes()));
        return p;
    }

    public AttendanceResponse toAttendance(Map<String, Object> r) {
        AttendanceResponse a = new AttendanceResponse();
        a.setAttendanceId(toInt(r.get("attendance_id"))); a.setEmpId(toInt(r.get("emp_id")));
        a.setEmpName((String) r.get("EmpName")); a.setEmpCode((String) r.get("emp_code"));
        a.setAttendanceDate(str(r.get("attendance_date")));
        a.setCheckIn((String) r.get("check_in")); a.setCheckOut((String) r.get("check_out"));
        a.setDurationMinutes(r.get("duration_minutes") != null ? toInt(r.get("duration_minutes")) : 0);
        a.setStatus((String) r.get("status")); a.setSource((String) r.get("source")); a.setNotes((String) r.get("notes"));
        return a;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public String str(Object o)   { return o != null ? o.toString() : null; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
