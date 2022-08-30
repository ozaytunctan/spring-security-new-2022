package com.ozaytunctan.mapper;

import com.ozaytunctan.dtos.UserRoleDto;
import com.ozaytunctan.entity.UserRole;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class RoleMapperImpl implements UserRoleMapper {


    @Autowired
    @Qualifier("delegate")
    UserRoleMapper delegate;

//    @Mappings()
    @Override
    public List<UserRoleDto> userRolesToUserRoleDtos(List<UserRole> roles) {
        return  delegate.userRolesToUserRoleDtos(roles);
    }
}
