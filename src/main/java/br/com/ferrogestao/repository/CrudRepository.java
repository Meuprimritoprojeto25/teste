package br.com.ferrogestao.repository;

import java.util.List;

public interface CrudRepository<T> {
    T find(Long id);
    List<T> findAll();
    long count();
    T save(T entity);
    void delete(T entity);
}