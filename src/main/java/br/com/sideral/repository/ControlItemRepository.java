package br.com.sideral.repository;
import java.util.List; import br.com.sideral.domain.ControlItem; import br.com.sideral.domain.TrafficColor; import org.springframework.stereotype.Repository;
@Repository public class ControlItemRepository extends HibernateRepository<ControlItem,Long> {
 public ControlItemRepository(){super(ControlItem.class);}
 public List<ControlItem> findByColor(TrafficColor color){return sessionFactory.getCurrentSession().createQuery("from ControlItem where trafficColor=:color order by area, code").setParameter("color",color).list();}
 public List<ControlItem> findByArea(String area){return sessionFactory.getCurrentSession().createQuery("from ControlItem where area=:area order by code").setString("area",area).list();}
}