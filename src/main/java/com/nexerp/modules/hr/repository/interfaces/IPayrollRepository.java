package com.nexerp.modules.hr.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IPayrollRepository {

    List<Map<String, Object>> getPayroll(Map<String, Object> params);
    Map<String, Object> getPayrollById(Integer id, Integer companyId);
    void processPayroll(Integer month, Integer year, Integer companyId);
    void markPayrollPaid(Integer id, Integer companyId);
}
