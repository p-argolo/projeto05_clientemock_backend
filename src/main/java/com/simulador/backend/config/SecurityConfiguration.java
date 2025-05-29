package com.simulador.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired private UserAuthenticationFilter userAuthenticationFilter;

  public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
    "/users/login",
  };

  public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
          "/users/authentication",
          "/transactions/create"
  };

  public static final String[] ENDPOINTS_CUSTOMER = {"/users/authentication/customer"};

  public static final String[] ENDPOINTS_ADMIN = {"/users/authentication/administrator"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> auth
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                    .permitAll()
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED)
                    .authenticated()
                    .requestMatchers(ENDPOINTS_ADMIN)
                    .hasRole("ADMINISTRATOR")
                    .requestMatchers(ENDPOINTS_CUSTOMER)
                    .hasRole("CUSTOMER")
                    .anyRequest()
                    .denyAll())
        .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
