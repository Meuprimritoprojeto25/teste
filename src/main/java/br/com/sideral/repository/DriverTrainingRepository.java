package br.com.sideral.repository;
import java.util.List; import br.com.sideral.domain.DriverTraining; import org.springframework.stereotype.Repository;
@Repository public class DriverTrainingRepository extends HibernateRepository<DriverTraining,Long> {
 public DriverTrainingRepository(){super(DriverTraining.class);}
 public List<DriverTraining> findExpired(){return sessionFactory.getCurrentSession().createQuery("from DriverTraining where expiration < current_date() order by expiration").list();}
}