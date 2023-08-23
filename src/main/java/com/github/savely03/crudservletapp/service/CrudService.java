package com.github.savely03.crudservletapp.service;

import java.util.List;

public interface CrudService<E, I> {
    List<E> findAll();

    E findById(I id);

    E save(E obj);

    E update(I id, E obj);

    void deleteById(I id);
}
