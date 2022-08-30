package com.ozaytunctan.controller;


import com.ozaytunctan.aspect.annotation.IdGuard;
import com.ozaytunctan.dtos.ServiceResultDto;
import com.ozaytunctan.dtos.UserRoleDto;
import com.ozaytunctan.services.UserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/role")
public class RoleController {


    private final UserRoleService userRoleService;

    public RoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @IdGuard(parameterIndex = 0)
    @GetMapping(path = "/{userId}")
    public ServiceResultDto<List<UserRoleDto>> getRolesByUserId(@PathVariable("userId")   Long userId) {
        return new ServiceResultDto<>(userRoleService.getUserRoles(userId));
    }


}
