package com.ozaytunctan.services;

import com.ozaytunctan.dtos.AuthenticationRequest;
import com.ozaytunctan.dtos.LoggedInUserDto;
import com.ozaytunctan.dtos.ServiceResultDto;

public interface AuthenticationService {
    ServiceResultDto<LoggedInUserDto> authenticate(AuthenticationRequest request);
}
