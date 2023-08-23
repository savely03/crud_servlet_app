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
public class ClientDto {
    private Long id;
    private String fullName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateBirthday;
    private Short gender;
}
