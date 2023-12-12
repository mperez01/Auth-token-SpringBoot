package com.mperez.controller;

import lombok.RequiredArgsConstructor;
import com.mperez.dto.UserDto;
import com.mperez.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

  private final UserService userService;

  @GetMapping("/user")
  public ResponseEntity<UserDto> getUser(final Principal connectedUser) {
    return ResponseEntity.ok(this.userService.getUserInfo(connectedUser));
  }
}
