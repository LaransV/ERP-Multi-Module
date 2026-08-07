package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class ScreenResponse {

        private Integer screenId;
        private String  screenCode;
        private String  screenName;
        private Integer moduleId;
        private String  moduleName;
        private String  route;
        private Integer sortOrder;
    
}
