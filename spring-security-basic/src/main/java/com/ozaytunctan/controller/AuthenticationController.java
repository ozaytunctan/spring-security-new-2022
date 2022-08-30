package com.ozaytunctan.controller;

import com.ozaytunctan.dtos.AuthenticationRequest;
import com.ozaytunctan.dtos.LoggedInUserDto;
import com.ozaytunctan.dtos.ServiceResultDto;
import com.ozaytunctan.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {


    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ServiceResultDto<LoggedInUserDto> login(@RequestBody() AuthenticationRequest request, HttpServletResponse response) {
        return  authenticationService.authenticate(request);
    }

    @PostMapping("/logout")
    public LoggedInUserDto doLogout(@RequestBody() LoggedInUserDto logoutUser) {
        return logoutUser;
    }

}
