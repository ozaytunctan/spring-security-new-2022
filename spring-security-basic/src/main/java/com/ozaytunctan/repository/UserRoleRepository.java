package com.ozaytunctan.repository;

import com.ozaytunctan.dtos.UserRoleDto;
import com.ozaytunctan.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select new com.ozaytunctan.dtos.UserRoleDto(u.id,r.id,r.name) from UserRole  ur join ur.role r join ur.user u where u.id=:userId")
    List<UserRoleDto> findByUserId(@Param("userId") Long userId);


}
