package br.com.sideral.repository;
import java.util.List; import br.com.sideral.domain.FollowUp; import org.springframework.stereotype.Repository;
@Repository public class FollowUpRepository extends HibernateRepository<FollowUp,Long> {
 public FollowUpRepository(){super(FollowUp.class);}
 public List<FollowUp> findByControlItem(Long itemId){return sessionFactory.getCurrentSession().createQuery("from FollowUp where controlItem.id=:id order by registeredAt desc").setLong("id",itemId).list();}
}