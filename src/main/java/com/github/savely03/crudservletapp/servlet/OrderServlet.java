package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.service.impl.OrderServiceImpl;
import com.github.savely03.crudservletapp.validation.OrderDtoValidator;
import jakarta.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/v1/order")
public class OrderServlet extends BaseServlet<OrderDto> {
    public OrderServlet() {
        super(OrderServiceImpl.getInstance(), OrderDtoValidator.getInstance());
    }

    @Override
    public OrderDto readObject(BufferedReader reader) throws IOException {
        return objectMapper.readValue(reader, OrderDto.class);
    }
}
