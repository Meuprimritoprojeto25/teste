package br.com.sideral.repository;

import java.util.List;
import br.com.sideral.domain.ItemControle;
import br.com.sideral.infra.HibernateUtil;

public class IndicadorRepository {
    public void salvar(ItemControle item) { HibernateUtil.currentSession().save(item); }
    public ItemControle porId(Long id) { return (ItemControle) HibernateUtil.currentSession().get(ItemControle.class, id); }
    @SuppressWarnings("unchecked")
    public List<ItemControle> listar() { return HibernateUtil.currentSession().createQuery("from ItemControle order by area, nome").list(); }
    public long porStatus(String status) {
        return ((Long)HibernateUtil.currentSession().createQuery("select count(*) from ItemControle where status = :s").setString("s", status).uniqueResult()).longValue();
    }
}