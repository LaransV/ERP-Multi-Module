package com.nexerp.modules.hr.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.hr.dto.request.AttendanceRequest;
import com.nexerp.modules.hr.dto.request.DeptRequest;
import com.nexerp.modules.hr.dto.request.EmployeeRequest;
import com.nexerp.modules.hr.dto.response.AttendanceResponse;
import com.nexerp.modules.hr.dto.response.HRMSDashboardResponse;
import com.nexerp.modules.hr.dto.response.DeptResponse;
import com.nexerp.modules.hr.dto.response.DesigResponse;
import com.nexerp.modules.hr.dto.response.EmployeeResponse;
import com.nexerp.modules.hr.dto.response.PayrollResponse;

import java.util.List;

public interface HrService {

    List<DeptResponse> listDepts();
    DeptResponse createDept(DeptRequest req);
    void deleteDept(Integer id);

    List<DesigResponse> listDesigs();

    PagedResponse<EmployeeResponse> listEmployees(int page, int size, String status, String search);
    EmployeeResponse getEmployee(Integer id);
    EmployeeResponse createEmployee(EmployeeRequest req);
    EmployeeResponse updateEmployee(Integer id, EmployeeRequest req);
    void deleteEmployee(Integer id);

    PagedResponse<AttendanceResponse> listAttendance(int page, int size, Integer empId, String fromDate, String toDate);
    AttendanceResponse createAttendance(AttendanceRequest req);
    AttendanceResponse updateAttendance(Integer id, AttendanceRequest req);
    void deleteAttendance(Integer id);

    PagedResponse<PayrollResponse> listPayroll(int page, int size, Integer month, Integer year);
    PayrollResponse getPayroll(Integer id);
    void processPayroll(Integer month, Integer year);
    void markPayrollPaid(Integer id);

    HRMSDashboardResponse getDashboard();
}
