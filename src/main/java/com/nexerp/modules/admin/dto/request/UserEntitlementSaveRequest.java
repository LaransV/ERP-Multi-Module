package com.nexerp.modules.admin.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserEntitlementSaveRequest {

        // List of screens to upsert for this user.
        // Send ONLY the screens that should be user-specific.
        // To reset a user back to role-defaults, call DELETE /users/{id}/entitlements.
        @NotNull
        private List<ScreenPermission> screens;

        @Data
        public static class ScreenPermission {
            @NotNull(message = "ScreenId is required")
            private Integer screenId;
            private boolean canCreate;
            private boolean canRead;
            private boolean canUpdate;
            private boolean canDelete;
        }
    
}
