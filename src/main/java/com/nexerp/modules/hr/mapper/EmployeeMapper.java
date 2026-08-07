package com.nexerp.modules.hr.mapper;

import com.nexerp.modules.hr.dto.request.EmployeeRequest;
import com.nexerp.modules.hr.dto.response.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmployeeMapper {

    public Map<String, Object> toEmployeeParams(EmployeeRequest r) {
        Map<String, Object> p = new HashMap<>();
        p.put("EmpCode",            nvl(r.getEmpCode()));
        p.put("FirstName",          r.getFirstName());
        p.put("LastName",           r.getLastName());
        p.put("Email",              nvl(r.getEmail()));
        p.put("Phone",              nvl(r.getPhone()));
        p.put("DeptId",             r.getDeptId() != null ? r.getDeptId() : 0);
        p.put("DesigId",            r.getDesigId() != null ? r.getDesigId() : 0);
        p.put("DateOfJoining",      r.getDateOfJoining());
        p.put("DateOfBirth",        nvl(r.getDateOfBirth()));
        p.put("Gender",             nvl(r.getGender()));
        p.put("EmploymentType",     nvl(r.getEmploymentType()));
        p.put("BasicSalary",        r.getBasicSalary() != null ? r.getBasicSalary() : BigDecimal.ZERO);
        p.put("PfNumber",           nvl(r.getPfNumber()));
        p.put("EsiNumber",          nvl(r.getEsiNumber()));
        p.put("PanNumber",          nvl(r.getPanNumber()));
        p.put("ReportingManagerId", r.getReportingManagerId() != null ? r.getReportingManagerId() : 0);
        p.put("Address",            nvl(r.getAddress()));
        p.put("City",               nvl(r.getCity()));
        p.put("State",              nvl(r.getState()));
        p.put("Status",             nvl(r.getStatus()));
        return p;
    }

    public EmployeeResponse toEmployee(Map<String, Object> r) {
        EmployeeResponse e = new EmployeeResponse();
        e.setEmpId(toInt(r.get("emp_id"))); e.setEmpCode((String) r.get("emp_code"));
        e.setFirstName((String) r.get("first_name")); e.setLastName((String) r.get("last_name"));
        e.setFullName((String) r.get("full_name")); e.setEmail((String) r.get("email")); e.setPhone((String) r.get("phone"));
        e.setDeptName((String) r.get("dept_name")); e.setDesigName((String) r.get("desig_name"));
        e.setDateOfJoining(str(r.get("date_of_joining"))); e.setDateOfBirth(str(r.get("date_of_birth")));
        e.setGender((String) r.get("gender")); e.setEmploymentType((String) r.get("employment_type")); e.setStatus((String) r.get("status"));
        e.setBasicSalary(toBD(r.get("basic_salary"))); e.setPfNumber((String) r.get("pf_number"));
        e.setEsiNumber((String) r.get("esi_number")); e.setPanNumber((String) r.get("pan_number"));
        e.setReportingManagerName((String) r.get("ReportingManagerName"));
        e.setAddress((String) r.get("address")); e.setCity((String) r.get("city")); e.setState((String) r.get("state"));
        e.setCreatedAt(str(r.get("created_at")));
        return e;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public String str(Object o)   { return o != null ? o.toString() : null; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
