package com.nexerp.modules.admin.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class EntitlementRequest {

        private Integer entitlementId;

        @NotNull(message = "Role ID is required")
        private Integer roleId;

        @NotNull(message = "Screen ID is required")
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
