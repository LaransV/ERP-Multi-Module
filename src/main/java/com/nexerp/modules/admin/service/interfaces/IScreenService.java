package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.modules.admin.dto.response.ScreenResponse;

import java.util.List;

public interface IScreenService {
    List<ScreenResponse> listScreens(Integer moduleId);
}
