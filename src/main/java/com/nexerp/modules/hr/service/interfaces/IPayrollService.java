package com.nexerp.modules.hr.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.hr.dto.response.PayrollResponse;

public interface IPayrollService {
    PagedResponse<PayrollResponse> listPayroll(int page, int size, Integer month, Integer year);
    PayrollResponse getPayroll(Integer id);
    void processPayroll(Integer month, Integer year);
    void markPayrollPaid(Integer id);
}
