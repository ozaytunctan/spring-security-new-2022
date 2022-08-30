package com.ozaytunctan.services.impl;

import com.ozaytunctan.constants.Constants;
import com.ozaytunctan.dtos.AuthenticationRequest;
import com.ozaytunctan.dtos.LoggedInUserDto;
import com.ozaytunctan.dtos.ServiceResultDto;
import com.ozaytunctan.security.model.UserDetailsWrapper;
import com.ozaytunctan.security.manager.JwtTokenManager;
import com.ozaytunctan.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenManager jwtTokenManager;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenManager jwtTokenManager) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public ServiceResultDto<LoggedInUserDto> authenticate(AuthenticationRequest request) {

        UserDetailsWrapper userDetails = (UserDetailsWrapper) this.userDetailsService.loadUserByUsername(request.getUserName());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword(), userDetails.getAuthorities());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoggedInUserDto inUser = LoggedInUserDto.of(userDetails.getUsername(), userDetails.getPassword())
                .roles(userDetails.getRoles())
                .token(jwtTokenManager.generate(userDetails, userDetails.getId()));

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(new Cookie(Constants.SESSION_ID, inUser.getToken()));

        return new ServiceResultDto<>(inUser);
    }
}
