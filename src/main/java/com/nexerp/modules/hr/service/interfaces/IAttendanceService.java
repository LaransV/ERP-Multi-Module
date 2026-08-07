package com.nexerp.modules.hr.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.hr.dto.request.AttendanceRequest;
import com.nexerp.modules.hr.dto.response.AttendanceResponse;

public interface IAttendanceService {
    PagedResponse<AttendanceResponse> listAttendance(int page, int size, Integer empId, String fromDate, String toDate);
    AttendanceResponse createAttendance(AttendanceRequest req);
    AttendanceResponse updateAttendance(Integer id, AttendanceRequest req);
    void deleteAttendance(Integer id);
}
