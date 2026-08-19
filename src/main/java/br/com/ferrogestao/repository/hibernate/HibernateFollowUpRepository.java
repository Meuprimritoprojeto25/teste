package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.enums.TrafficStatus;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.FollowUpRepository;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateFollowUpRepository extends AbstractHibernateRepository<FollowUp> implements FollowUpRepository {
    public HibernateFollowUpRepository() { super(FollowUp.class); }

    @SuppressWarnings("unchecked")
    public List<FollowUp> findLatest(int limit) {
        return session().createQuery("from FollowUp f join fetch f.item order by f.referenceDate desc, f.id desc")
                .setMaxResults(limit).list();
    }

    public FollowUp findLatestForItem(Long itemId) {
        return (FollowUp) session().createQuery(
                "from FollowUp f join fetch f.item where f.item.id=:itemId order by f.referenceDate desc, f.id desc")
                .setParameter("itemId", itemId).setMaxResults(1).uniqueResult();
    }

    public Map<TrafficStatus, Long> countLatestByStatus() {
        Map<TrafficStatus, Long> result = new EnumMap<TrafficStatus, Long>(TrafficStatus.class);
        for (TrafficStatus status : TrafficStatus.values()) { result.put(status, 0L); }
        @SuppressWarnings("unchecked")
        List<Object[]> rows = session().createQuery(
                "select f.status, count(f) from FollowUp f where f.id in " +
                "(select max(x.id) from FollowUp x group by x.item.id) group by f.status").list();
        for (Object[] row : rows) { result.put((TrafficStatus) row[0], (Long) row[1]); }
        return result;
    }
}