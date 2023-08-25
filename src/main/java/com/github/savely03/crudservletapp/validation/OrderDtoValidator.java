package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.OrderValidationException;

public class OrderDtoValidator extends Validator<OrderDto> {
    private static final OrderDtoValidator INSTANCE = new OrderDtoValidator();

    private OrderDtoValidator() {
    }

    public static OrderDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(OrderDto orderDto) {
        if (orderDto.getClientId() == null ||
            orderDto.getCarId() == null ||
            orderDto.getOrderDate() == null) {
            throw new OrderValidationException();
        }
    }

    @Override
    public void validateId(Long id) {
        if (id <= 0) {
            throw new OrderValidationException();
        }
    }
}
