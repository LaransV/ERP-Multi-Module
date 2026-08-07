package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class EntitlementResponse {

        private Integer entitlementId;
        private Integer roleId;
        private Integer screenId;
        private String  screenCode;
        private String  screenName;
        private String  moduleCode;
        private String  moduleName;
        private boolean canCreate;
        private boolean canRead;
        private boolean canUpdate;
        private boolean canDelete;
    
}
