package com.nexerp.modules.hr.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IEmployeeRepository {

    List<Map<String, Object>> getEmployees(Map<String, Object> params);
    Map<String, Object> getEmployeeById(Integer id, Integer companyId);
    Integer insertEmployee(Map<String, Object> params);
    void updateEmployee(Map<String, Object> params);
    void deleteEmployee(Integer id, Integer companyId);
}
