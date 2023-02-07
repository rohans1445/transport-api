package com.example.transportapi.security;

import com.example.transportapi.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(POST, "/api/bus").hasRole("ADMIN")
                .antMatchers(GET, "/api/bus/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(GET, "/api/bus").hasRole("ADMIN")
                .antMatchers(GET, "/api/bus/available").hasRole("ADMIN")
                .antMatchers(POST, "/api/buspass/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(GET, "/api/officeLocation/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(POST, "/api/officeLocation").hasRole("ADMIN")
                .antMatchers(GET, "/api/routes/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(POST, "/api/routes").hasAnyRole("ADMIN")
                .antMatchers(PUT, "/api/routes/{id}").hasAnyRole("ADMIN")
                .antMatchers(GET, "/api/routes/{id}/stops").hasAnyRole("ADMIN", "USER")
                .antMatchers(GET, "/api/stops/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(POST, "/api/stops").hasRole("ADMIN")
                .antMatchers(GET, "/api/trips/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(PATCH, "/api/trips/{id}/status").hasAnyRole("ADMIN", "USER")
                .antMatchers(POST, "/api/trips/{id}/verify").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/user/**").authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
