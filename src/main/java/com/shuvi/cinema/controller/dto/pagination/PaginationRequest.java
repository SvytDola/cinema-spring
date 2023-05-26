package com.shuvi.cinema.controller.dto.pagination;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class PaginationRequest {

    @Max(value = 100)
    @Positive
    private Integer size = 25;

    @Min(value = 0)
    private Integer start = 0;

}
