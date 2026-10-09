package com.hotel.saas.hotel_saas_backend.security;

import com.hotel.saas.hotel_saas_backend.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class HotelUserPrincipal implements UserDetails {

    private final Long userId;
    private final Long hotelId;
    private final String email;
    private final String password;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    public HotelUserPrincipal(User user) {
        this.userId = user.getId();
        this.hotelId = user.getHotel().getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.active = Boolean.TRUE.equals(user.getActive());

        this.authorities = List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name()
                )
        );
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

