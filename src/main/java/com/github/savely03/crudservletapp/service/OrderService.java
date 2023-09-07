package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.exception.OrderNotFoundException;
import com.github.savely03.crudservletapp.mapper.CarMapper;
import com.github.savely03.crudservletapp.mapper.ClientMapper;
import com.github.savely03.crudservletapp.mapper.OrderMapper;
import com.github.savely03.crudservletapp.model.Car;
import com.github.savely03.crudservletapp.model.Client;
import com.github.savely03.crudservletapp.model.Order;
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

    @SneakyThrows
    @Override
    public List<OrderDto> findAll() {
        try (Connection connection = ConnectionPool.getConnection()) {
            return orderRepository.findAll(connection).stream()
                    .map(orderMapper::toDto)
                    .toList();
        }
    }

    @SneakyThrows
    @Override
    public OrderDto findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return orderRepository.findById(id, connection)
                    .map(orderMapper::toDto)
                    .orElseThrow(() -> new OrderNotFoundException(id));
        }
    }

    @SneakyThrows
    @Override
    public OrderDto save(OrderDto orderDto) {
        Connection connection = ConnectionPool.getConnectionWithNoAutoCommit();
        return wrapInTransaction(() -> {
            if (orderDto.getClientDto() != null) {
                Client client = clientRepository.saveOrUpdate(clientMapper.toEntity(orderDto.getClientDto()), connection);
                orderDto.setClientId(client.getId());
            }
            if (orderDto.getCarDto() != null) {
                Car car = carRepository.saveOrUpdate(carMapper.toEntity(orderDto.getCarDto()), connection);
                orderDto.setCarId(car.getId());
            }
            Order order = orderRepository.saveOrUpdate(orderMapper.toEntity(orderDto), connection);
            order.setCar(carRepository.findById(orderDto.getCarId(), connection).orElse(null));
            order.setClient(clientRepository.findById(orderDto.getClientId(), connection).orElse(null));
            return orderMapper.toDto(order);
        }, connection);
    }

    @SneakyThrows
    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return wrapInTransaction(() -> {
                orderRepository.findById(id, connection).orElseThrow(
                        () -> new OrderNotFoundException(id)
                );
                orderDto.setId(id);
                Car car = carRepository.findById(orderDto.getCarId(), connection)
                        .orElseThrow(() -> new CarNotFoundException(orderDto.getCarId()));
                Client client = clientRepository.findById(orderDto.getClientId(), connection)
                        .orElseThrow(() -> new ClientNotFoundException(orderDto.getClientId()));
                Order order = orderRepository.saveOrUpdate(orderMapper.toEntity(orderDto), connection);
                order.setCar(car);
                order.setClient(client);
                return orderMapper.toDto(order);
            }, connection);
        }
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            if (!orderRepository.deleteById(id, connection)) {
                throw new OrderNotFoundException(id);
            }
        }
    }

    @SneakyThrows
    @Override
    public boolean exists(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return orderRepository.exists(id, connection);
        }
    }
}
