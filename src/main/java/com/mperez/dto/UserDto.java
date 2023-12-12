package com.mperez.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDto {

  private String email;

  private String name;

  private String lastname;

  private LocalDate birthday;
}
