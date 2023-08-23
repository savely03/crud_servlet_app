package com.github.savely03.crudservletapp.dto;

import com.github.savely03.crudservletapp.adapter.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private Long clientId;
    private Long carId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate orderDate;
}
