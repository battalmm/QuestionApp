package com.korkmazyusufcan.questionapp.service.security;

import com.korkmazyusufcan.questionapp.dto.request.AuthenticationRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.response.AuthenticationResponse;
import com.korkmazyusufcan.questionapp.entity.user.Roles;
import com.korkmazyusufcan.questionapp.entity.user.User;
import com.korkmazyusufcan.questionapp.repository.UserRepository;
import com.korkmazyusufcan.questionapp.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserDetailsServiceImplementation userDetailService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService,
                                 UserRepository userRepository,
                                 UserService userService,
                                 UserDetailsServiceImplementation userDetailService,
                                 AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        //TODO
        // COMPLETE
        return new AuthenticationResponse();
    }

    public AuthenticationResponse register(UserCreateRequest userCreateRequest) {
        userService.isEmailExist(userCreateRequest.getEmail());

        User newUser = User.builder()
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .role(Roles.USER)
                .build();

        UserDetails user = userDetailService.loadUserByUsername(userRepository.save(newUser).getEmail());
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}