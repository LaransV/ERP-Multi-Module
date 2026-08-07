package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.ScreenResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScreenMapper {

    public ScreenResponse toScreen(Map<String, Object> r) {
        ScreenResponse s = new ScreenResponse();
        s.setScreenId(toInt(r.get("screen_id")));
        s.setScreenCode((String) r.get("screen_code"));
        s.setScreenName((String) r.get("screen_name"));
        s.setModuleId(toInt(r.get("module_id")));
        s.setModuleName((String) r.get("module_name"));
        s.setRoute((String) r.get("route"));
        s.setSortOrder(r.get("sort_order") != null ? toInt(r.get("sort_order")) : 0);
        return s;
    }

    public int  toInt(Object o)  { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long toLong(Object o) { if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
}
