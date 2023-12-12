package com.mperez.config;

import com.mperez.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;

  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authz -> {
              authz.requestMatchers("/v1/login", "/v1/register", "/h2-console/**").permitAll();
              authz.anyRequest().authenticated();
            })
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(this.authenticationProvider)
        .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .httpBasic(AbstractHttpConfigurer::disable);

    return http.build();
  }
}
