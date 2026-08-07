package com.nexerp.modules.hr.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.hr.repository.interfaces.IAttendanceRepository;
import com.nexerp.modules.hr.sql.AttendanceSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository implements IAttendanceRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getAttendance(Map<String, Object> params) {
                int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(AttendanceSql.GET_ATTENDANCE, params);
    }

    @Override
    public Map<String, Object> getAttendanceById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("AttendanceId", id);
        return jdbc.queryOne(AttendanceSql.GET_ATTENDANCE_BY_ID, params);
    }

    @Override
    public Integer insertAttendance(Map<String, Object> params) {
        return jdbc.executeAndGetId(AttendanceSql.INSERT_ATTENDANCE, params);
    }

    @Override
    public void updateAttendance(Map<String, Object> params) {
        jdbc.execute(AttendanceSql.UPDATE_ATTENDANCE, params);
    }

    @Override
    public void deleteAttendance(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("AttendanceId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(AttendanceSql.DELETE_ATTENDANCE, params);
    }
}
