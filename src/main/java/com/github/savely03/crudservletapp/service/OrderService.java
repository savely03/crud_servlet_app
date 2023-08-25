package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.OrderDto;

import java.util.List;

public interface OrderService extends CrudService<OrderDto, Long> {

    List<Integer> findMonthsWithMostOrdersCars();
}
