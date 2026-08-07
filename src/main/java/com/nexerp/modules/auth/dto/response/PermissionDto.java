package com.nexerp.modules.auth.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class PermissionDto {

        private String  moduleCode;
        private String  moduleName;
        private String  screenCode;
        private String  screenName;
        private boolean canCreate;
        private boolean canRead;
        private boolean canUpdate;
        private boolean canDelete;
    
}
