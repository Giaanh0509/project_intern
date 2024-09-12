package com.example.bookstore.security;

import com.example.bookstore.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsersService usersService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(usersService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,CustomAuthenticationSuccessHandler successHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(
                configurer -> configurer
                        .requestMatchers("/account").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/books/reviews/**").authenticated()
                        .anyRequest().permitAll()
        ).formLogin(
                form -> form.loginPage("/sessions/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(successHandler)
                        .permitAll()
        ).logout(
                logout -> logout.permitAll()
        ).exceptionHandling(
                configurer -> configurer.accessDeniedPage("/showPage403")
        ).csrf(csrf -> csrf
                .ignoringRequestMatchers("/books/reviews/**")
        );

        return httpSecurity.build();
    }
}