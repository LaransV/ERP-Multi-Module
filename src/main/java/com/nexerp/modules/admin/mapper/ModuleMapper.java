package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.ModuleResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ModuleMapper {

    public ModuleResponse toModule(Map<String, Object> r) {
        ModuleResponse m = new ModuleResponse();
        m.setModuleId(toInt(r.get("module_id")));
        m.setModuleCode((String) r.get("module_code"));
        m.setModuleName((String) r.get("module_name"));
        m.setIcon((String) r.get("icon"));
        m.setSortOrder(r.get("sort_order") != null ? toInt(r.get("sort_order")) : 0);
        return m;
    }

    public int  toInt(Object o)  { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long toLong(Object o) { if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
}
