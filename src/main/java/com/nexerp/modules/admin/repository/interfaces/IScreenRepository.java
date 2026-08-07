package com.nexerp.modules.admin.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IScreenRepository {

    List<Map<String, Object>> getScreens(Integer moduleId);
}
