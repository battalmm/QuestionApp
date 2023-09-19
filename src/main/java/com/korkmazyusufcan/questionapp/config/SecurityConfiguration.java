package com.korkmazyusufcan.questionapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    //TODO
    // RELOAD ALL SECURİTY CONFİG 
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**")
                                .anonymous()
                        .anyRequest()
                                .permitAll()
                        )
                .csrf(csrf ->
                        // BAD - CSRF protection shouldn't be disabled
                        csrf.disable()
                );


        return http.build();
    }
}


