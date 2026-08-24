package com.shoply.application.dto;
import java.util.UUID;
public record AdminCustomerDto(UUID id, String email, String role) {}
