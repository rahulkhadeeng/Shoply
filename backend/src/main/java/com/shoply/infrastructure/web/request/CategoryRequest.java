package com.shoply.infrastructure.web.request;

import jakarta.validation.constraints.*;

public record CategoryRequest(@NotBlank @Size(max=120) String name,
                              @NotBlank @Pattern(regexp="[a-z0-9]+(?:-[a-z0-9]+)*") String slug,
                              @NotBlank @Size(max=40) String icon) {}
