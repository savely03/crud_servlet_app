package com.github.savely03.crudservletapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto {
    private Long id;
    private String model;
    private String color;
    private Double engineCapacity;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;
    private BigDecimal price;
}
