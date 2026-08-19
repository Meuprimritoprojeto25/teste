package br.com.sideral.service;

import java.math.BigDecimal;
import java.util.List;
import br.com.sideral.domain.ItemEstoque;
import br.com.sideral.infra.Transacao;
import br.com.sideral.repository.EstoqueRepository;

public class EstoqueService {
    private final EstoqueRepository repository=new EstoqueRepository();
    public ItemEstoque cadastrar(final String descricao, final String categoria, final BigDecimal quantidade, final BigDecimal minimo) {
        if (vazio(descricao) || vazio(categoria) || quantidade == null || minimo == null || quantidade.signum()<0 || minimo.signum()<0)
            throw new RegraNegocioException("Informe descrição, categoria e quantidades válidas.");
        return Transacao.executar(new Transacao.Operacao<ItemEstoque>() { public ItemEstoque executar() {
            ItemEstoque item=new ItemEstoque(descricao.trim(),categoria.trim(),quantidade,minimo); repository.salvar(item); return item;
        }});
    }
    public List<ItemEstoque> listar(){return Transacao.executar(new Transacao.Operacao<List<ItemEstoque>>(){public List<ItemEstoque> executar(){return repository.listar();}});}
    private boolean vazio(String valor){return valor==null || valor.trim().length()==0;}
}