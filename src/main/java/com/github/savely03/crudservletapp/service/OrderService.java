package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.OrderNotFoundException;
import com.github.savely03.crudservletapp.mapper.CarMapper;
import com.github.savely03.crudservletapp.mapper.ClientMapper;
import com.github.savely03.crudservletapp.mapper.OrderMapper;
import com.github.savely03.crudservletapp.model.Car;
import com.github.savely03.crudservletapp.model.Client;
import com.github.savely03.crudservletapp.repository.CarRepository;
import com.github.savely03.crudservletapp.repository.ClientRepository;
import com.github.savely03.crudservletapp.repository.OrderRepository;
import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.util.List;

public class OrderService implements CrudService<OrderDto> {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderRepository orderRepository = OrderRepository.getInstance();
    private final CarRepository carRepository = CarRepository.getInstance();
    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);
    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    private OrderService() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @SneakyThrows
    @Override
    public OrderDto save(OrderDto orderDto) {
        Connection connection = ConnectionPool.getConnectionWithNoAutoCommit();
        return wrapInTransaction(() -> {
            if (orderDto.getClientDto() != null) {
                Client client = clientRepository.save(clientMapper.toEntity(orderDto.getClientDto()), connection);
                orderDto.setClientId(client.getId());
            }
            if (orderDto.getCarDto() != null) {
                Car car = carRepository.save(carMapper.toEntity(orderDto.getCarDto()), connection);
                orderDto.setCarId(car.getId());
            }
            return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto), connection));
        }, connection);
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id)
        );
        orderDto.setId(id);
        orderRepository.update(orderMapper.toEntity(orderDto));
        return orderDto;
    }

    @Override
    public void deleteById(Long id) {
        if (!orderRepository.deleteById(id)) {
            throw new OrderNotFoundException(id);
        }
    }

    @Override
    public boolean exists(Long id) {
        return orderRepository.exists(id);
    }
}
