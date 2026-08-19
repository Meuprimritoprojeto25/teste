package br.com.sideral.repository;

import java.math.BigDecimal;
import java.util.List;
import br.com.sideral.domain.RegistroProducao;
import br.com.sideral.infra.HibernateUtil;

public class ProducaoRepository {
    public void salvar(RegistroProducao registro) { HibernateUtil.currentSession().save(registro); }
    @SuppressWarnings("unchecked")
    public List<RegistroProducao> recentes() {
        return HibernateUtil.currentSession().createQuery("from RegistroProducao order by data desc").setMaxResults(12).list();
    }
    public BigDecimal total() {
        BigDecimal total=(BigDecimal)HibernateUtil.currentSession().createQuery("select sum(toneladas) from RegistroProducao").uniqueResult();
        return total == null ? BigDecimal.ZERO : total;
    }
}