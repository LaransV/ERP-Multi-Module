package com.nexerp.modules.auth.service.interfaces;

import com.nexerp.security.UserPrincipal;

/**
 * Cross-cutting security-context contract used by every module to resolve the
 * currently authenticated user (e.g. auth.currentPrincipal().getCompanyId()).
 * Kept as a single, stable interface (no per-entity split) since nearly every
 * other module's service layer depends on it directly.
 * See ISessionService / IProfileService / ICompanyDirectoryService for the
 * rest of the auth module's own (per-entity) functionality.
 */
public interface AuthService {
    UserPrincipal currentPrincipal();
}
