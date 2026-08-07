package com.nexerp.modules.hr.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface IAttendanceRepository {

    List<Map<String, Object>> getAttendance(Map<String, Object> params);
    Map<String, Object> getAttendanceById(Integer id);
    Integer insertAttendance(Map<String, Object> params);
    void updateAttendance(Map<String, Object> params);
    void deleteAttendance(Integer id, Integer companyId);
}
