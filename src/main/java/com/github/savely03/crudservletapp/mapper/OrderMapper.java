package com.github.savely03.crudservletapp.mapper;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper extends GeneralMapper<OrderDto, Order> {
}
