package com.korkmazyusufcan.questionapp.controller;

import com.korkmazyusufcan.questionapp.dto.request.AuthenticationRequest;
import com.korkmazyusufcan.questionapp.dto.request.UserCreateRequest;
import com.korkmazyusufcan.questionapp.dto.response.AuthenticationResponse;
import com.korkmazyusufcan.questionapp.service.security.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;}

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody UserCreateRequest userCreateRequest){
        return authenticationService.register(userCreateRequest);
    }

}
