package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserEntitlementRow {

        private Integer moduleId;
        private String  moduleCode;
        private String  moduleName;
        private Integer moduleSortOrder;
        private Integer screenId;
        private String  screenCode;
        private String  screenName;
        private Integer screenSortOrder;
        private Integer userEntitlementId;   // null  → not yet overridden
        private boolean isUserOverride;      // false → showing role default
        private boolean canCreate;
        private boolean canRead;
        private boolean canUpdate;
        private boolean canDelete;
    
}
