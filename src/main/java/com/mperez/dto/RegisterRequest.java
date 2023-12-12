package com.mperez.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotNull @NotEmpty private String email;

  @NotNull @NotEmpty private String password;

  private String name;

  private String lastname;

  private LocalDate birthday;
}
