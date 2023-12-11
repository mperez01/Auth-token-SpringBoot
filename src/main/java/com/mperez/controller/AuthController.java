package com.mperez.controller;

import com.mperez.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import com.mperez.dto.LoginRequest;
import com.mperez.dto.AuthenticationResponse;
import com.mperez.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @Validated @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authenticationService.login(loginRequest));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @Validated @RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.ok(authenticationService.register(registerRequest));
  }
}
