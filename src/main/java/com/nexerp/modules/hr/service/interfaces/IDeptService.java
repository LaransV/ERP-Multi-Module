package com.nexerp.modules.hr.service.interfaces;

import com.nexerp.modules.hr.dto.request.DeptRequest;
import com.nexerp.modules.hr.dto.response.DeptResponse;

import java.util.List;

public interface IDeptService {
    List<DeptResponse> listDepts();
    DeptResponse createDept(DeptRequest req);
    void deleteDept(Integer id);
}
