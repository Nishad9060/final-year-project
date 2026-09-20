package com.footonomy.auth.security;

import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Reads the authenticated user's id, set as the Authentication name by
 * {@link JwtAuthenticationFilter}. Only ever populated on /api/users/me/** requests — every
 * other endpoint runs with no Authentication in the context at all (docs/03_TRD.md §6).
 */
@Component
public class CurrentUserProvider {

    public UUID requireCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user in security context");
        }
        return UUID.fromString(authentication.getName());
    }
}
