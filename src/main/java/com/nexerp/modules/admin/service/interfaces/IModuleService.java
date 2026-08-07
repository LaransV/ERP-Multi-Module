package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.modules.admin.dto.response.ModuleResponse;

import java.util.List;

public interface IModuleService {
    List<ModuleResponse> listModules();
}
