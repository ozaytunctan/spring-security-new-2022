package com.ozaytunctan.dtos;

import java.io.Serializable;

public class UserRoleDto implements Serializable {

    private Long userId;

    private Long roleId;

    private String roleName;

    public UserRoleDto() {
    }


    public UserRoleDto(Long userId, Long roleId, String roleName) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
