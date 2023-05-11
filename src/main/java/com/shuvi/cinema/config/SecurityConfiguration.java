package com.shuvi.cinema.config;

import com.shuvi.cinema.filter.JwtAuthenticationFilter;
import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.shuvi.cinema.common.ResourceConstant.*;
import static org.springframework.http.HttpMethod.*;

/**
 * Конфигурация для авторизации.
 *
 * @author Shuvi
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .disable()
                .requestMatchers()
                .and()
                .authorizeHttpRequests(requests ->
                        requests
                                .antMatchers(POST, GENRE_API_PATH + "/**").authenticated()
                                .antMatchers(PUT, GENRE_API_PATH + "/**").authenticated()
                                .antMatchers(DELETE, GENRE_API_PATH + "/**").authenticated()

                                .antMatchers(POST, CINEMA_API_PATH + "/**").authenticated()
                                .antMatchers(PUT, CINEMA_API_PATH + "/**").authenticated()
                                .antMatchers(DELETE, CINEMA_API_PATH + "/**").authenticated()

                                .antMatchers(POST, REVIEW_API_PATH + "/**").authenticated()
                                .antMatchers(POST, REVIEW_API_PATH + "/**").authenticated()
                                .antMatchers(DELETE, REVIEW_API_PATH + "/**").authenticated()

                                .antMatchers(POST, USER_API_PATH + "/**").authenticated()
                                .antMatchers(PUT, USER_API_PATH + "/**").authenticated()
                                .antMatchers(DELETE, USER_API_PATH + "/**").authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
