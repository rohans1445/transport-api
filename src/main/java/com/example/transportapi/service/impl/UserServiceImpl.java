package com.example.transportapi.service.impl;

import com.example.transportapi.entity.User;
import com.example.transportapi.repository.UserRepository;
import com.example.transportapi.security.CustomUserDetails;
import com.example.transportapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username - " + username));
        return user.map(CustomUserDetails::new).get();
    }
}
