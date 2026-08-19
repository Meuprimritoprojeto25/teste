package br.com.ferrogestao.repository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHibernateRepository<T> implements CrudRepository<T> {
    @Autowired
    private SessionFactory sessionFactory;
    private final Class<T> entityClass;

    protected AbstractHibernateRepository(Class<T> entityClass) { this.entityClass = entityClass; }
    protected Session session() { return sessionFactory.getCurrentSession(); }

    @SuppressWarnings("unchecked")
    public T find(Long id) { return (T) session().get(entityClass, id); }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return session().createCriteria(entityClass)
                .setResultTransformer(org.hibernate.Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public long count() {
        return ((Number) session().createQuery("select count(e) from " + entityClass.getSimpleName() + " e")
                .uniqueResult()).longValue();
    }

    @SuppressWarnings("unchecked")
    public T save(T entity) { return (T) session().merge(entity); }

    public void delete(T entity) { session().delete(entity); }
}