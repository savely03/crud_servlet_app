package com.github.savely03.crudservletapp.mapper;

import com.github.savely03.crudservletapp.dto.FullOrderDto;
import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper extends GeneralMapper<OrderDto, Order> {
    @Mapping(target = "clientId", source = "dto.clientDto.id")
    @Mapping(target = "carId", source = "dto.carDto.id")
    Order toEntity(FullOrderDto dto);

}
