package com.nexerp.modules.hr.mapper;

import com.nexerp.modules.hr.dto.response.PayrollResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PayrollMapper {

    public PayrollResponse toPayroll(Map<String, Object> r) {
        PayrollResponse p = new PayrollResponse();
        p.setPayrollId(toInt(r.get("payroll_id"))); p.setEmpId(toInt(r.get("emp_id")));
        p.setEmpName((String) r.get("EmpName")); p.setEmpCode((String) r.get("emp_code"));
        p.setMonth(toInt(r.get("month"))); p.setYear(toInt(r.get("year")));
        p.setDaysWorked(toInt(r.get("days_worked"))); p.setLopDays(toBD(r.get("lop_days")));
        p.setGrossSalary(toBD(r.get("gross_salary"))); p.setTotalDeductions(toBD(r.get("total_deductions"))); p.setNetSalary(toBD(r.get("net_salary")));
        p.setPfEmployee(toBD(r.get("pf_employee"))); p.setPfEmployer(toBD(r.get("pf_employer")));
        p.setEsiEmployee(toBD(r.get("esi_employee"))); p.setEsiEmployer(toBD(r.get("esi_employer")));
        p.setTdsAmount(toBD(r.get("tds_amount"))); p.setProfessionalTax(toBD(r.get("professional_tax")));
        p.setStatus((String) r.get("status")); p.setProcessedAt(str(r.get("processed_at"))); p.setPaidAt(str(r.get("paid_at")));
        return p;
    }

    public String nvl(String s)   { return s != null ? s : ""; }
    public String str(Object o)   { return o != null ? o.toString() : null; }
    public int    toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long   toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o) { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
}
