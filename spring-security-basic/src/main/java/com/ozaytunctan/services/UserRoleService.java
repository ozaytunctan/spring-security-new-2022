package com.ozaytunctan.services;

import com.ozaytunctan.dtos.UserRoleDto;

import java.util.List;

public interface UserRoleService {

     List<UserRoleDto> getUserRoles(Long userId);
}
