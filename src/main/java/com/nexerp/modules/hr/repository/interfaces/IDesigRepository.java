package com.nexerp.modules.hr.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IDesigRepository {

    List<Map<String, Object>> getDesignations(Integer companyId);
}
