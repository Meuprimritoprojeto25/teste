package br.com.ferrogestao.repository;

import br.com.ferrogestao.domain.User;

public interface UserRepository extends CrudRepository<User> {
    User findByEmail(String email);
}