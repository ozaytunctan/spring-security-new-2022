package com.ozaytunctan.startup;

import com.ozaytunctan.entity.Role;
import com.ozaytunctan.entity.User;
import com.ozaytunctan.entity.UserRole;
import com.ozaytunctan.repository.RoleRepository;
import com.ozaytunctan.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUsers implements CommandLineRunner {


    private RoleRepository roleRepository;
    private final UserRepository userRepository;

    public StartUsers(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role role = new Role("ADMIN");
//        role=this.roleRepository.saveAndFlush(role);

        User user = new User();
        user.setUserName("ozaytunctan");
        user.setPassword("1234");

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setActive(true);
        user.addUserRole(userRole);

        this.userRepository.saveAndFlush(user);

  //      Role role2 = new Role("USER");
//        role=this.roleRepository.saveAndFlush(role);
//
//        User user2 = new User();
////        user2.setUserName("test");
//        user2.setPassword("1234");
//
//        UserRole userRole2 = new UserRole();
//        userRole2.setRole(role2);
//        userRole2.setActive(true);
//        user2.addUserRole(userRole2);
//
//        this.userRepository.saveAndFlush(user2);

    }
}
