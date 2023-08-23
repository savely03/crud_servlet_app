package com.github.savely03.crudservletapp.mapper;

public interface GeneralMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
}
