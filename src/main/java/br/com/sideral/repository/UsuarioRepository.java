package br.com.sideral.repository;

import java.util.List;
import org.hibernate.Query;
import br.com.sideral.domain.Usuario;
import br.com.sideral.infra.HibernateUtil;

public class UsuarioRepository {
    public void salvar(Usuario usuario) { HibernateUtil.currentSession().save(usuario); }
    public Usuario porMatricula(String matricula) {
        Query q = HibernateUtil.currentSession().createQuery("from Usuario where matricula = :matricula");
        q.setString("matricula", matricula); return (Usuario) q.uniqueResult();
    }
    @SuppressWarnings("unchecked")
    public List<Usuario> listar() { return HibernateUtil.currentSession().createQuery("from Usuario order by nome").list(); }
    public Usuario porId(Long id) { return (Usuario) HibernateUtil.currentSession().get(Usuario.class, id); }
    public long ativos() { return ((Long) HibernateUtil.currentSession().createQuery("select count(*) from Usuario where ativo = true").uniqueResult()).longValue(); }
    public long total() { return ((Long) HibernateUtil.currentSession().createQuery("select count(*) from Usuario").uniqueResult()).longValue(); }
}