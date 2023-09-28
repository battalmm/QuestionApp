package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.request.AuthenticationRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.response.AuthenticationResponse;
import com.korkmazyusufcan.questionapp.service.UserService;
import com.korkmazyusufcan.questionapp.service.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody UserCreateRequest userCreateRequest){
        return authenticationService.register(userCreateRequest);
    }

}
