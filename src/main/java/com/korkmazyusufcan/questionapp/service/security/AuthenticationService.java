package com.korkmazyusufcan.questionapp.service.security;

import com.korkmazyusufcan.questionapp.dto.request.AuthenticationRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.response.AuthenticationResponse;
import com.korkmazyusufcan.questionapp.entity.user.Roles;
import com.korkmazyusufcan.questionapp.entity.user.User;
import com.korkmazyusufcan.questionapp.repository.UserRepository;
import com.korkmazyusufcan.questionapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImplementation userDetailService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService,
                                 UserRepository userRepository,
                                 UserService userService,
                                 PasswordEncoder passwordEncoder,
                                 UserDetailsServiceImplementation userDetailService,
                                 AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        log.debug("Çalıştı");

        //TODO
        // USER NOT FOUND EXCEPTION
        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse register(UserCreateRequest userCreateRequest) {
        userService.isEmailExist(userCreateRequest.getEmail());

        User newUser = User.builder()
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .role(Roles.USER)
                .build();

        UserDetails user = userDetailService.loadUserByUsername(userRepository.save(newUser).getEmail());
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}