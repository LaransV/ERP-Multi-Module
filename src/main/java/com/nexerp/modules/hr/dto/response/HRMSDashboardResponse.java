package com.nexerp.modules.hr.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class HRMSDashboardResponse {

        private long   totalEmployees;
        private long   activeEmployees;
        private long   onNotice;
        private long   todayPresent;
        private long   todayAbsent;
        private long   newJoiningThisMonth;
        private long   separationsThisMonth;
        private List<DeptCountDto> departmentWise;
    
}
