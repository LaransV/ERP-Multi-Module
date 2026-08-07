package com.nexerp.modules.hr.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.hr.dto.request.EmployeeRequest;
import com.nexerp.modules.hr.dto.response.EmployeeResponse;

public interface IEmployeeService {
    PagedResponse<EmployeeResponse> listEmployees(int page, int size, String status, String search);
    EmployeeResponse getEmployee(Integer id);
    EmployeeResponse createEmployee(EmployeeRequest req);
    EmployeeResponse updateEmployee(Integer id, EmployeeRequest req);
    void deleteEmployee(Integer id);
}
