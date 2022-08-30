package com.ozaytunctan.entity;

import com.ozaytunctan.enums.Gender;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "idGenerator", allocationSize = 1, initialValue = 1, sequenceName = "USERS_SQ")
public class User extends BaseEntity<Long> {


    @Column(name = "USER_NAME",unique = true)
    private String userName;

    private String password;

    @Column(name = "IDENTITY_NUMBER",unique = true,length = 11)
    private String identityNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender = Gender.OTHER;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserRole> roles=new ArrayList<>();

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void addUserRole(UserRole role) {
        role.setUser(this);
        this.roles.add(role);
    }

    public void addUserRoles(UserRole... roles) {
        for (UserRole role : roles) {
            addUserRole(role);
        }
    }
}
