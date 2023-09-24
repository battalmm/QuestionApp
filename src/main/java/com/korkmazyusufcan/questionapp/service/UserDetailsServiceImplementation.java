package com.korkmazyusufcan.questionapp.service;

import com.korkmazyusufcan.questionapp.entity.user.JwtUser;
import com.korkmazyusufcan.questionapp.entity.user.User;
import com.korkmazyusufcan.questionapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getAuthorities()
        );
    }


}
