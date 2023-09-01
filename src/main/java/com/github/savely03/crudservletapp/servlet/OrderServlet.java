package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.service.impl.OrderServiceImpl;
import com.github.savely03.crudservletapp.validation.OrderDtoValidator;
import jakarta.servlet.annotation.WebServlet;

import static com.github.savely03.crudservletapp.mapper.JsonMapper.OBJECT_MAPPER;

@WebServlet("/api/v1/order")
public class OrderServlet extends BaseServlet<OrderDto> {
    public OrderServlet() {
        super(OrderServiceImpl.getInstance(), OrderDtoValidator.getInstance(), s -> OBJECT_MAPPER.readValue(s, OrderDto.class));
    }
}
