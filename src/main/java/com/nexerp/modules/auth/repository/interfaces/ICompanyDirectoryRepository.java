package com.nexerp.modules.auth.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface ICompanyDirectoryRepository {

    List<Map<String, Object>> getUserCompanies(Integer userId);
}
