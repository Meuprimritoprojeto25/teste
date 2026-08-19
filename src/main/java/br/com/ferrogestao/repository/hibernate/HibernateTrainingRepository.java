package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.Driver;
import br.com.ferrogestao.domain.DriverTraining;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.TrainingRepository;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateTrainingRepository extends AbstractHibernateRepository<DriverTraining> implements TrainingRepository {
    public HibernateTrainingRepository() { super(DriverTraining.class); }
    @SuppressWarnings("unchecked")
    public List<DriverTraining> findExpiring(int days) {
        Calendar limit = Calendar.getInstance();
        limit.add(Calendar.DAY_OF_MONTH, days);
        return session().createQuery("from DriverTraining t join fetch t.driver where t.expirationDate <= :limit order by t.expirationDate")
                .setParameter("limit", limit.getTime()).list();
    }
    @SuppressWarnings("unchecked")
    public List<Driver> findDrivers() {
        return session().createQuery("from Driver d order by d.name").list();
    }
    public Driver findDriver(Long id) { return (Driver) session().get(Driver.class, id); }
    public Driver saveDriver(Driver driver) { session().saveOrUpdate(driver); return driver; }
}