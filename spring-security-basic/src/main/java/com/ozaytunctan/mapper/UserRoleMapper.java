package com.ozaytunctan.mapper;

import com.ozaytunctan.dtos.UserRoleDto;
import com.ozaytunctan.entity.Role;
import com.ozaytunctan.entity.UserRole;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {


    UserRoleDto userRoleToUserRoleDto(UserRole role);

    UserRole userRoleDtoToUserRole(UserRoleDto role);

    List<UserRoleDto> userRolesToUserRoleDtos(List<UserRole> roles);

}
