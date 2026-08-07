package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class ModuleResponse {

        private Integer moduleId;
        private String  moduleCode;
        private String  moduleName;
        private String  icon;
        private Integer sortOrder;
    
}
