package com.github.savely03.crudservletapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private Long clientId;
    private Long carId;
    private ClientDto clientDto;
    private CarDto carDto;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate orderDate;
}
