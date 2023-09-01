//package com.github.savely03.crudservletapp.service.impl;
//
//import com.github.javafaker.Faker;
//import com.github.savely03.crudservletapp.dto.OrderDto;
//import com.github.savely03.crudservletapp.mapper.OrderMapper;
//import com.github.savely03.crudservletapp.model.Order;
//import com.github.savely03.crudservletapp.repository.OrderRepository;
//import com.github.savely03.crudservletapp.service.OrderService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//import org.mockito.MockedStatic;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.stream.LongStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.mockito.Mockito.*;
//
//class OrderServiceImplTest {
//    private static OrderService out;
//    private static OrderRepository orderRepository;
//    private final Faker faker = new Faker();
//    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
//
//    @BeforeAll
//    static void beforeAll() {
//        try (MockedStatic<OrderRepository> orderRepositoryMocked = mockStatic(OrderRepository.class)) {
//            orderRepository = mock(OrderRepository.class);
//            orderRepositoryMocked.when(OrderRepository::getInstance).thenReturn(orderRepository);
//            out = OrderServiceImpl.getInstance();
//        }
//    }
//
//    @BeforeEach
//    void setUp() {
//        reset(orderRepository);
//    }
//
//    @Test
//    void findAllTest() {
//        List<Order> orders = generateOrders();
//        List<OrderDto> ordersDto = orders.stream()
//                .map(orderMapper::toDto)
//                .toList();
//
//        when(orderRepository.findAll()).thenReturn(orders);
//
//        assertThat(out.findAll()).isEqualTo(ordersDto);
//        assertThat(out.findAll()).hasSize(orders.size());
//    }
//
//    @Test
//    void findByIdTest() {
//        Order order = generateOrder();
//        OrderDto orderDto = orderMapper.toDto(order);
//
//        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
//
//        assertThat(out.findById(orderDto.getId())).isEqualTo(orderDto);
//    }
//
//    @Test
//    void findByIdWhenOrderNotFoundTest() {
//        assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(
//                () -> out.findById(1L)
//        );
//    }
//
//    @Test
//    void saveTest() {
//        Order order = generateOrder();
//        OrderDto orderDto = orderMapper.toDto(order);
//
//        when(orderRepository.save(order)).thenReturn(order);
//
//        assertThat(out.save(orderDto)).isEqualTo(orderDto);
//    }
//
//    @Test
//    void saveWhenIncorrectValidationTest() {
//        OrderDto orderDto = OrderDto.builder().build();
//
//        assertThatExceptionOfType(OrderValidationException.class).isThrownBy(
//                () -> out.save(orderDto)
//        );
//    }
//
//    @Test
//    void updateTest() {
//        Order order = generateOrder();
//        OrderDto orderDto = orderMapper.toDto(order);
//
//        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
//        when(orderRepository.update(order)).thenReturn(order);
//
//        assertThat(out.update(orderDto.getId(), orderDto)).isEqualTo(orderDto);
//    }
//
//    @Test
//    void updateWhenOrderNotFoundTest() {
//        Order order = generateOrder();
//        OrderDto orderDto = orderMapper.toDto(order);
//
//        assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(
//                () -> out.update(orderDto.getId(), orderDto)
//        );
//    }
//
//    @Test
//    void updateWhenIncorrectValidationTest() {
//        OrderDto orderDto = OrderDto.builder().build();
//
//        assertThatExceptionOfType(OrderValidationException.class).isThrownBy(
//                () -> out.update(orderDto.getId(), orderDto)
//        );
//    }
//
//    @Test
//    void deleteByIdTest() {
//        Long id = ThreadLocalRandom.current().nextLong(1, 1000);
//
//        when(orderRepository.deleteById(id)).thenReturn(true);
//
//        out.deleteById(id);
//
//        verify(orderRepository).deleteById(id);
//    }
//
//    @Test
//    void findMonthsWithMostOrdersCarsTest() {
//        List<Integer> months = generateOrders(5).stream()
//                .map(Order::getOrderDate)
//                .map(LocalDate::getMonth)
//                .map(Month::getValue)
//                .toList();
//
//        when(orderRepository.findMonthsWithMostOrdersCars()).thenReturn(months);
//
//        assertThat(out.findMonthsWithMostOrdersCars()).isEqualTo(months);
//        assertThat(out.findMonthsWithMostOrdersCars()).hasSize(months.size());
//    }
//
//    private Order generateOrder() {
//        return Order.builder()
//                .id(1L)
//                .orderDate(LocalDate.now())
//                .carId(faker.random().nextLong())
//                .clientId(faker.random().nextLong())
//                .build();
//    }
//
//    private List<Order> generateOrders(int count) {
//        return LongStream.iterate(1, i -> i + 1)
//                .limit(count)
//                .mapToObj(i -> {
//                    Order order = generateOrder();
//                    order.setId(i);
//                    return order;
//                })
//                .toList();
//    }
//
//    private List<Order> generateOrders() {
//        return generateOrders(10);
//    }
//}