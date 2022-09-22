package com.example.transportapi.service;

import com.example.transportapi.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User saveUser(User user);

    boolean ifUsernameExists(String username);

    boolean ifEmailExists(String email);
}
