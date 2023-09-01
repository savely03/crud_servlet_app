package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.exception.ValidationException;
import com.github.savely03.crudservletapp.service.CarService;
import com.github.savely03.crudservletapp.service.ClientService;
import com.github.savely03.crudservletapp.service.impl.CarServiceImpl;
import com.github.savely03.crudservletapp.service.impl.ClientServiceImpl;

public class OrderDtoValidator extends Validator<OrderDto> {
    private static final OrderDtoValidator INSTANCE = new OrderDtoValidator();
    private final CarService carService = CarServiceImpl.getInstance();
    private final ClientService clientService = ClientServiceImpl.getInstance();

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
            throw new ValidationException();
        }
        if (!clientService.exists(orderDto.getClientId())) {
            throw new ClientNotFoundException(orderDto.getClientId());
        }
        if (!carService.exists(orderDto.getCarId())) {
            throw new CarNotFoundException(orderDto.getCarId());
        }

    }
}
