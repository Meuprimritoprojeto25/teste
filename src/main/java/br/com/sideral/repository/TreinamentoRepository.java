package br.com.sideral.repository;

import java.util.List;
import org.hibernate.Query;
import br.com.sideral.domain.TreinamentoMotorista;
import br.com.sideral.infra.HibernateUtil;

public class TreinamentoRepository {
    public void salvar(TreinamentoMotorista item) { HibernateUtil.currentSession().save(item); }
    @SuppressWarnings("unchecked")
    public List<TreinamentoMotorista> listar() {
        return HibernateUtil.currentSession().createQuery("from TreinamentoMotorista order by validade asc").list();
    }
    public long pendentes() {
        Query q=HibernateUtil.currentSession().createQuery("select count(*) from TreinamentoMotorista where situacao <> 'REGULAR'");
        return ((Long)q.uniqueResult()).longValue();
    }
}