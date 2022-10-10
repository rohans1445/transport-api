package com.example.transportapi.service.impl;

import com.example.transportapi.dto.BusPassResponseDTO;
import com.example.transportapi.entity.BusPass;
import com.example.transportapi.entity.User;
import com.example.transportapi.mapper.BusPassMapper;
import com.example.transportapi.repository.UserRepository;
import com.example.transportapi.security.CustomUserDetails;
import com.example.transportapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusPassMapper busPassMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username - " + username));
        return user.map(CustomUserDetails::new).get();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username - " + username));
        return user.get();
    }

    @Override
    public User saveUser(User user) {
        user.setRoles("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean ifUsernameExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public boolean ifEmailExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return findByUsername(username);
    }

    @Override
    public List<BusPassResponseDTO> getUserPasses(String username) {
        User user = findByUsername(username);
        List<BusPass> passes = user.getPasses();
        return busPassMapper.toBusPassResponseDTOs(passes);
    }
}
