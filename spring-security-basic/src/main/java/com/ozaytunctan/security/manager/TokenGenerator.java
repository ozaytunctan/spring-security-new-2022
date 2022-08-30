package com.ozaytunctan.security.manager;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenGenerator {

    public String generate(UserDetails user,Long userId);

    public boolean isValid(String token);

    public boolean isTokenExpired(String token);

}
