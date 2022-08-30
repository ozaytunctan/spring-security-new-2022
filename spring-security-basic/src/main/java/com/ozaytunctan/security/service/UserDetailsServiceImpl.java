package com.ozaytunctan.security.service;

import com.ozaytunctan.entity.Role;
import com.ozaytunctan.entity.UserRole;
import com.ozaytunctan.repository.UserRepository;
import com.ozaytunctan.security.model.UserDetailsWrapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.ozaytunctan.entity.User user = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the username:" + username));


        //create custom wrapper user detail model
        return new UserDetailsWrapper(user);
    }
}
