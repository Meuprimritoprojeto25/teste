package br.com.sideral.repository;
import br.com.sideral.domain.User; import org.springframework.stereotype.Repository;
@Repository public class UserRepository extends HibernateRepository<User,Long> {
 public UserRepository(){super(User.class);}
 public User findByLogin(String login){return (User)sessionFactory.getCurrentSession().createQuery("from User where login = :login").setString("login",login).uniqueResult();}
}