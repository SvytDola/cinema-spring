package com.shuvi.cinema.controller.dto.genre;

import lombok.Data;

import java.util.UUID;

@Data
public class GenreResponse {
    private UUID id;
    private String name;
    private String description;
}
