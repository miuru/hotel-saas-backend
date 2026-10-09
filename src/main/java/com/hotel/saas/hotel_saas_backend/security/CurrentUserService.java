package com.hotel.saas.hotel_saas_backend.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private HotelUserPrincipal getPrincipal() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal()
                instanceof HotelUserPrincipal principal)) {

            throw new AuthenticationCredentialsNotFoundException(
                    "Authenticated user is required"
            );
        }

        return principal;
    }

    public Long getCurrentUserId() {
        return getPrincipal().getUserId();
    }

    public Long getCurrentHotelId() {
        return getPrincipal().getHotelId();
    }

    public String getCurrentUserEmail() {
        return getPrincipal().getUsername();
    }
}

