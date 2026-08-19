package br.com.sideral.repository;
import java.util.List; import br.com.sideral.domain.Deployment; import org.springframework.stereotype.Repository;
@Repository public class DeploymentRepository extends HibernateRepository<Deployment,Long> {
 public DeploymentRepository(){super(Deployment.class);}
 public List<Deployment> findOpen(){return sessionFactory.getCurrentSession().createQuery("from Deployment where status <> 'CONCLUIDO' order by dueDate").list();}
}