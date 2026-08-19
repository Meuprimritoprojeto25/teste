package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.ControlItem;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.ControlItemRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateControlItemRepository extends AbstractHibernateRepository<ControlItem> implements ControlItemRepository {
    public HibernateControlItemRepository() { super(ControlItem.class); }
    @SuppressWarnings("unchecked")
    public List<ControlItem> findActive() {
        return session().createQuery("from ControlItem i where i.active = true order by i.area, i.code").list();
    }
}