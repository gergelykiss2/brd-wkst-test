package com.gergelytamas.brdwksttest.service;

import java.util.List;
import java.util.Optional;

/** T - class */
public interface BaseService<T> {

    List<T> findAll();

    Optional<T> findById(Integer id);

    T save(T t);

    T update(T t, Integer id);

    void delete(Integer id);
}
