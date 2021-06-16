package com.innowisegroup.simpleblog.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericRepository <T extends Serializable> {
    List<T> findAll();
    Optional<T> findById(long id);
    void create(T user);
    void update(T user);
    void delete(T user);
    void deleteById(long id);
}
