package com.github.savely03.crudservletapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientWithCntCarsDto {
    private Long id;
    private String fullName;
    private Integer countCars;
}
