package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.ProductionRecord;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.ProductionRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateProductionRepository extends AbstractHibernateRepository<ProductionRecord> implements ProductionRepository {
    public HibernateProductionRepository() { super(ProductionRecord.class); }
    @SuppressWarnings("unchecked")
    public List<Object[]> totalsByPlant() {
        return session().createQuery("select p.plant, sum(p.producedTons), avg(p.qualityIndex) from ProductionRecord p group by p.plant").list();
    }
}