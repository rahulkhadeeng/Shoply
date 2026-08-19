package com.shoply.infrastructure.web.request;
import jakarta.validation.constraints.*;
public record LoginRequest(@NotBlank @Email String email,@NotBlank String password) {}
