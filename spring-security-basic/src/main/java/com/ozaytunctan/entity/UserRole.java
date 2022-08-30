package com.ozaytunctan.entity;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLES")
@SequenceGenerator(name = "idGenerator", allocationSize = 1, initialValue = 1, sequenceName = "USER_ROLES_SQ")
public class UserRole extends BaseEntity<Long> {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    public UserRole() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
