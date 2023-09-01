package com.github.savely03.crudservletapp.repository;

import java.util.List;
import java.util.Optional;

// Идентификатор у всех реализаций - Long
public interface CrudRepository<E> {
    List<E> findAll();

    Optional<E> findById(Long id);

    E save(E obj);

    E update(E obj);

    boolean deleteById(Long id);

    boolean exists(Long id);
}
