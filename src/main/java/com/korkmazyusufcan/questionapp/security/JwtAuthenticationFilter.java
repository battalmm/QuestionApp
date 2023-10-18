package com.korkmazyusufcan.questionapp.security;

import com.korkmazyusufcan.questionapp.service.security.JwtService;
import com.korkmazyusufcan.questionapp.service.security.UserDetailsServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String bearer;
        final String userMail;
        final String jwtToken;
        bearer = request.getHeader("Authorization");
        if(StringUtils.isEmpty(bearer) || !StringUtils.startsWith(bearer,"Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = bearer.substring(7);
        log.info("JWT: " + jwtToken);

        userMail = jwtService.extractUsername(jwtToken);
        log.debug("Mail: " + userMail);

        if (StringUtils.isNotEmpty(userMail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImplementation.loadUserByUsername(userMail);
            if(jwtService.isTokenValid(jwtToken,userDetails)){
                log.debug("User: " + userDetails);
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}