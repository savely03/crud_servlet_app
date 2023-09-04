package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.exception.ValidationException;
import com.github.savely03.crudservletapp.service.CarService;
import com.github.savely03.crudservletapp.service.ClientService;

public class OrderDtoValidator extends Validator<OrderDto> {
    private static final OrderDtoValidator INSTANCE = new OrderDtoValidator();
    private final CarService carService = CarService.getInstance();
    private final ClientService clientService = ClientService.getInstance();
    private final CarDtoValidator carDtoValidator = CarDtoValidator.getInstance();
    private final ClientDtoValidator clientDtoValidator = ClientDtoValidator.getInstance();

    private OrderDtoValidator() {
    }

    public static OrderDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(OrderDto orderDto) {
        if (orderDto.getOrderDate() == null) {
            throw new ValidationException();
        }

        if ((orderDto.getClientId() != null && !clientService.exists(orderDto.getClientId()))
            && orderDto.getClientDto() == null) {
            throw new ClientNotFoundException(orderDto.getClientId());
        }
        if ((orderDto.getCarId() != null && !carService.exists(orderDto.getCarId()))
            && orderDto.getCarDto() == null) {
            throw new CarNotFoundException(orderDto.getCarId());
        }

        if (orderDto.getCarDto() != null) {
            carDtoValidator.validate(orderDto.getCarDto());
        }
        if (orderDto.getClientDto() != null) {
            clientDtoValidator.validate(orderDto.getClientDto());
        }
    }
}
