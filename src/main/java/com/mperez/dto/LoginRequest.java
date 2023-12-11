package com.mperez.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;
    
}
