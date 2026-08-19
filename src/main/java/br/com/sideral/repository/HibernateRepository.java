package br.com.sideral.repository;
import java.io.Serializable; import java.util.List; import org.hibernate.SessionFactory; import org.springframework.beans.factory.annotation.Autowired;
public abstract class HibernateRepository<T, ID extends Serializable> implements GenericRepository<T, ID> {
 private final Class<T> type;
 @Autowired protected SessionFactory sessionFactory;
 protected HibernateRepository(Class<T> type){this.type=type;}
 public T findById(ID id){return (T)sessionFactory.getCurrentSession().get(type,id);}
 public List<T> findAll(){return sessionFactory.getCurrentSession().createCriteria(type).list();}
 public T save(T entity){sessionFactory.getCurrentSession().saveOrUpdate(entity); return entity;}
 public void delete(T entity){sessionFactory.getCurrentSession().delete(entity);}
}