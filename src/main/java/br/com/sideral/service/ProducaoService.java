package br.com.sideral.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import br.com.sideral.domain.RegistroProducao;
import br.com.sideral.infra.Transacao;
import br.com.sideral.repository.ProducaoRepository;

public class ProducaoService {
    private final ProducaoRepository repository = new ProducaoRepository();
    public RegistroProducao apontar(final Date data, final String forno, final BigDecimal toneladas, final BigDecimal qualidade) {
        if (data == null || vazio(forno) || toneladas == null || qualidade == null || toneladas.signum() <= 0 || qualidade.signum() < 0 || qualidade.compareTo(new BigDecimal("100")) > 0)
            throw new RegraNegocioException("Apontamento inválido. Qualidade deve estar entre 0 e 100.");
        return Transacao.executar(new Transacao.Operacao<RegistroProducao>() { public RegistroProducao executar() {
            RegistroProducao registro=new RegistroProducao(data, forno.trim(), toneladas, qualidade); repository.salvar(registro); return registro;
        }});
    }
    public List<RegistroProducao> recentes() { return Transacao.executar(new Transacao.Operacao<List<RegistroProducao>>() { public List<RegistroProducao> executar(){return repository.recentes();}}); }
    private boolean vazio(String valor) { return valor == null || valor.trim().length() == 0; }
}