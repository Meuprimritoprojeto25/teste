package br.com.sideral.service;

import java.util.Date;
import java.util.List;
import br.com.sideral.domain.TreinamentoMotorista;
import br.com.sideral.infra.Transacao;
import br.com.sideral.repository.TreinamentoRepository;

public class TreinamentoService {
    private final TreinamentoRepository repository = new TreinamentoRepository();
    public TreinamentoMotorista registrar(final String motorista, final String curso, final Date validade) {
        if (vazio(motorista) || vazio(curso) || validade == null) throw new RegraNegocioException("Motorista, treinamento e validade são obrigatórios.");
        final String situacao = validade.before(new Date()) ? "VENCIDO" : "REGULAR";
        return Transacao.executar(new Transacao.Operacao<TreinamentoMotorista>() { public TreinamentoMotorista executar() {
            TreinamentoMotorista item=new TreinamentoMotorista(motorista.trim(), curso.trim(), validade, situacao);
            repository.salvar(item); return item;
        }});
    }
    public List<TreinamentoMotorista> listar() { return Transacao.executar(new Transacao.Operacao<List<TreinamentoMotorista>>() { public List<TreinamentoMotorista> executar(){return repository.listar();}}); }
    private boolean vazio(String valor) { return valor == null || valor.trim().length() == 0; }
}