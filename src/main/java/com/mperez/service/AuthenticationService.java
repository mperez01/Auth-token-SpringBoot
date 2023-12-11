package com.mperez.service;

import com.mperez.dto.AuthenticationResponse;
import com.mperez.dto.LoginRequest;
import com.mperez.dto.RegisterRequest;
import com.mperez.model.Role;
import com.mperez.model.User;
import com.mperez.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse login(final LoginRequest loginRequest) {
    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword()));

    final var user = this.userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
    user.setLastLogin(LocalDateTime.now());
    this.userRepository.save(user);

    return this.createAuthenticationResponse(user);
  }

  public AuthenticationResponse register(final RegisterRequest registerRequest) {

    final var user =
        User.builder()
            .name(registerRequest.getName())
            .lastname(registerRequest.getLastname())
            .email(registerRequest.getEmail())
            .password(this.passwordEncoder.encode(registerRequest.getPassword()))
            .birthday(registerRequest.getBirthday())
            .role(Role.USER)
            .creationDate(LocalDateTime.now())
            .build();

    this.userRepository.save(user);

    return this.createAuthenticationResponse(user);
  }

  private AuthenticationResponse createAuthenticationResponse(final User user) {
    final var jwtToken = this.jwtService.generateToken(user);

    return AuthenticationResponse.builder().token(jwtToken).build();
  }
}
