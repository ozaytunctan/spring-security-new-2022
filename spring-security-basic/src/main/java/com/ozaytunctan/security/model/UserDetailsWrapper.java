package com.ozaytunctan.security.model;

import com.ozaytunctan.entity.User;
import com.ozaytunctan.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsWrapper implements UserDetails {


    private User user;

    public UserDetailsWrapper(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(r->new SimpleGrantedAuthority("ROLE_"+r))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return this.user.getId();
    }

    public List<String> getRoles() {
        return Optional
                .ofNullable(this.user.getRoles())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(r -> r.getRole().getName())
                .collect(Collectors.toList());
    }
}
