package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.KpiSnapshot;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.KpiRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateKpiRepository extends AbstractHibernateRepository<KpiSnapshot> implements KpiRepository {
    public HibernateKpiRepository() { super(KpiSnapshot.class); }
}