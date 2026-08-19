package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.R3GReport;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.R3GRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateR3GRepository extends AbstractHibernateRepository<R3GReport> implements R3GRepository {
    public HibernateR3GRepository() { super(R3GReport.class); }
}