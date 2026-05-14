package org.ipt.smartqueue.service.auth;

import lombok.AllArgsConstructor;
import org.ipt.smartqueue.data.enums.RoleEnum;
import org.ipt.smartqueue.data.model.User;
import org.ipt.smartqueue.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No authenticated user");
        }
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));
    }

    public boolean hasRole(RoleEnum role) {
        return getCurrentUser().getRole() == role;
    }

    public boolean isAdmin() {
        return hasRole(RoleEnum.ADMIN);
    }

    public boolean isOwner() {
        return hasRole(RoleEnum.OWNER);
    }

    public boolean isClient() {
        return hasRole(RoleEnum.CLIENT);
    }

    public boolean isCurrentUser(UUID userId) {
        return getCurrentUser().getId().equals(userId);
    }

    public void requireRole(RoleEnum role) {
        if (!hasRole(role)) {
            throw new AccessDeniedException("Required role: " + role);
        }
    }

    public void requireAdminOrOwner() {
        RoleEnum role = getCurrentUser().getRole();
        if (role != RoleEnum.ADMIN && role != RoleEnum.OWNER) {
            throw new AccessDeniedException("Admin or Owner role required");
        }
    }
}