package com.github.savely03.crudservletapp.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, I> {
    List<E> findAll();

    Optional<E> findById(I id);

    E save(E obj);

    E update(E obj);

    boolean deleteById(I id);
}
