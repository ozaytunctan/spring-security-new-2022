package com.ozaytunctan.services.impl;

import com.ozaytunctan.dtos.UserRoleDto;
import com.ozaytunctan.mapper.UserRoleMapper;
import com.ozaytunctan.repository.UserRoleRepository;
import com.ozaytunctan.services.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    private final UserRoleRepository userRoleRepository;


    public UserRoleServiceImpl(UserRoleMapper userRoleMapper, UserRoleRepository userRoleRepository) {
        this.userRoleMapper = userRoleMapper;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRoleDto> getUserRoles(Long userId) {
        return this.userRoleRepository.findByUserId(userId);
    }
}
