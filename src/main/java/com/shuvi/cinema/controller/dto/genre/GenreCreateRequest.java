package com.shuvi.cinema.controller.dto.genre;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class GenreCreateRequest {
    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String name;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String description;

}
