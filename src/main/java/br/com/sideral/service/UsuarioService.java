package br.com.sideral.service;

import java.util.List;
import br.com.sideral.domain.Usuario;
import br.com.sideral.infra.Transacao;
import br.com.sideral.repository.UsuarioRepository;

public class UsuarioService {
    private final UsuarioRepository repository = new UsuarioRepository();
    public Usuario cadastrar(final String nome, final String matricula, final String email, final String perfil) {
        validar(nome, matricula, email, perfil);
        return Transacao.executar(new Transacao.Operacao<Usuario>() {
            public Usuario executar() {
                if (repository.porMatricula(matricula.trim()) != null) throw new RegraNegocioException("A matrícula já está cadastrada.");
                Usuario usuario=new Usuario(nome.trim(), matricula.trim(), email.trim(), perfil.trim());
                repository.salvar(usuario); return usuario;
            }
        });
    }
    public List<Usuario> listar() { return Transacao.executar(new Transacao.Operacao<List<Usuario>>() { public List<Usuario> executar(){ return repository.listar(); }}); }
    public void alternarAtivacao(final Long id) {
        Transacao.executar(new Transacao.Operacao<Void>() { public Void executar() {
            Usuario u=repository.porId(id); if (u == null) throw new RegraNegocioException("Usuário não encontrado.");
            u.setAtivo(!u.isAtivo()); return null;
        }});
    }
    private void validar(String nome, String matricula, String email, String perfil) {
        if (vazio(nome) || vazio(matricula) || vazio(email) || vazio(perfil)) throw new RegraNegocioException("Preencha todos os campos do usuário.");
        if (!email.contains("@")) throw new RegraNegocioException("Informe um e-mail válido.");
    }
    private boolean vazio(String valor) { return valor == null || valor.trim().length() == 0; }
}