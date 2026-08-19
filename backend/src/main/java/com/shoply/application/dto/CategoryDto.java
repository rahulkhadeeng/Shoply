package com.shoply.application.dto;
import java.util.UUID;
public record CategoryDto(UUID id, String name, String slug, String icon, int itemCount) {}
