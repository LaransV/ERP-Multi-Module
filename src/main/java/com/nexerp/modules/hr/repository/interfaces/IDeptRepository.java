package com.nexerp.modules.hr.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IDeptRepository {

    List<Map<String, Object>> getDepartments(Integer companyId);
    Integer insertDepartment(Map<String, Object> params);
    void deleteDepartment(Integer id, Integer companyId);
}
