package com.example.amazinggamesbackend.config;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.security.JWTFilter;
import com.example.amazinggamesbackend.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JWTFilter filter;
    @Autowired
    private MyUserDetailsService userDetailService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http ,BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic().disable()
                .cors()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/auth/**" ,"/users/info")
                .permitAll()
                .antMatchers("/games/**")
                .permitAll()
                .antMatchers(HttpMethod.POST ,"/orders/**")
                .hasRole("USER")
                .antMatchers(HttpMethod.GET ,"/orders/**" ,"/games/**" ,"users/**","cart/**")
                .permitAll()
                .antMatchers(HttpMethod.POST ,"/orders/**" ,"/games/**" ,"users/**","cart/**")
                .hasRole("USER")
                .antMatchers(HttpMethod.DELETE ,"/orders/**" ,"/games/**" ,"users/**","cart/**")
                .permitAll()
                .and()
                .userDetailsService(userDetailService)
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request ,response ,authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED ,"Unauthorized"))
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter ,UsernamePasswordAuthenticationFilter.class).headers().frameOptions().disable();

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
