package com.ozaytunctan.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROLES")
@SequenceGenerator(name = "idGenerator", allocationSize = 1, initialValue = 1, sequenceName = "ROLES_SQ")
public class Role extends BaseEntity<Long> {

    @Column(name = "NAME",unique = true)
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "role")
    public List<UserRole> userRoles=new ArrayList<>();

    public Role() {
        super();
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
