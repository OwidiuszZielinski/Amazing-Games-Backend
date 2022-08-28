package com.example.amazinggamesbackend.config;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    public SecurityConfig(UserDetailsServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    private UserDetailsServiceImpl userDetailService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http ,BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf()
                .disable()
                .authorizeRequests()
                        .antMatchers(HttpMethod.DELETE)
                        .hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET , "/games/**")
                        .hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/games/**")
                        .hasRole("ADMIN")
                        .antMatchers("/login/**")
                        .anonymous()
                        .anyRequest()
                        .authenticated().and()
                .cors().and()
                .httpBasic();
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
