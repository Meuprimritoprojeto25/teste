package br.com.sideral.repository;

import java.util.List;
import br.com.sideral.domain.ItemEstoque;
import br.com.sideral.infra.HibernateUtil;

public class EstoqueRepository {
    public void salvar(ItemEstoque item) { HibernateUtil.currentSession().save(item); }
    @SuppressWarnings("unchecked")
    public List<ItemEstoque> listar() { return HibernateUtil.currentSession().createQuery("from ItemEstoque order by descricao").list(); }
    public long criticos() {
        return ((Long)HibernateUtil.currentSession().createQuery("select count(*) from ItemEstoque where quantidade < minimo").uniqueResult()).longValue();
    }
}