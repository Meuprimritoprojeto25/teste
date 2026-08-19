package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.User;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserRepository extends AbstractHibernateRepository<User> implements UserRepository {
    public HibernateUserRepository() { super(User.class); }
    public User findByEmail(String email) {
        return (User) session().createQuery("from User where lower(email) = lower(:email)")
                .setParameter("email", email).uniqueResult();
    }
}