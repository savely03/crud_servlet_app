package com.github.savely03.crudservletapp.service;

import java.util.List;

// Идентификатор у всех реализаций - Long
public interface CrudService<E> {
    List<E> findAll();

    E findById(Long id);

    E save(E obj);

    E update(Long id, E obj);

    void deleteById(Long id);

    boolean exists(Long id);
}
