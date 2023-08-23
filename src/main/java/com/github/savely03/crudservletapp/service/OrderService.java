package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.OrderNotFoundException;
import com.github.savely03.crudservletapp.mapper.OrderMapper;
import com.github.savely03.crudservletapp.repository.OrderRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class OrderService implements CrudService<OrderDto, Long> {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderRepository orderRepository = OrderRepository.getInstance();
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

    @Override
    public OrderDto save(OrderDto orderDto) {
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
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
}
