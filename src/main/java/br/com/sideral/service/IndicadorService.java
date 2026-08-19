package br.com.sideral.service;

import java.math.BigDecimal;
import java.util.List;
import br.com.sideral.domain.ItemControle;
import br.com.sideral.infra.Transacao;
import br.com.sideral.repository.IndicadorRepository;

public class IndicadorService {
    private final IndicadorRepository repository = new IndicadorRepository();
    public ItemControle criar(final String nome, final String area, final BigDecimal meta, final BigDecimal atual) {
        if (vazio(nome) || vazio(area) || meta == null || atual == null || meta.signum() <= 0 || atual.signum() < 0)
            throw new RegraNegocioException("Informe nome, área e valores positivos para o indicador.");
        return Transacao.executar(new Transacao.Operacao<ItemControle>() { public ItemControle executar() {
            ItemControle item=new ItemControle(nome.trim(), area.trim(), meta, atual); repository.salvar(item); return item;
        }});
    }
    public void apontar(final Long id, final BigDecimal valor) {
        if (valor == null || valor.signum() < 0) throw new RegraNegocioException("O valor apontado deve ser positivo.");
        Transacao.executar(new Transacao.Operacao<Void>() { public Void executar() {
            ItemControle item=repository.porId(id); if (item == null) throw new RegraNegocioException("Indicador não encontrado.");
            item.atualizar(valor); return null;
        }});
    }
    public List<ItemControle> listar() { return Transacao.executar(new Transacao.Operacao<List<ItemControle>>() { public List<ItemControle> executar(){return repository.listar();}}); }
    private boolean vazio(String valor) { return valor == null || valor.trim().length() == 0; }
}