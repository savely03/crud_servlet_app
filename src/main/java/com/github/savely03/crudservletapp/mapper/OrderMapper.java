package com.github.savely03.crudservletapp.mapper;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class OrderMapper implements GeneralMapper<OrderDto, Order> {

    protected final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);
    protected final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Override
    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "client.id", source = "clientId")
    public abstract Order toEntity(OrderDto dto);

    @Override
    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "carDto", expression = "java(carMapper.toDto(entity.getCar()))")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientDto", expression = "java(clientMapper.toDto(entity.getClient()))")
    public abstract OrderDto toDto(Order entity);
}
