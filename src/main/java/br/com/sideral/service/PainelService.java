package br.com.sideral.service;

import java.math.BigDecimal;
import java.util.List;
import br.com.sideral.domain.ItemControle;
import br.com.sideral.domain.RegistroProducao;
import br.com.sideral.infra.Transacao;
import br.com.sideral.repository.EstoqueRepository;
import br.com.sideral.repository.IndicadorRepository;
import br.com.sideral.repository.ProducaoRepository;
import br.com.sideral.repository.TreinamentoRepository;
import br.com.sideral.repository.UsuarioRepository;

public class PainelService {
    public static class Resumo {
        private long usuariosAtivos, treinamentosPendentes, estoqueCritico, verdes, amarelos, vermelhos;
        private BigDecimal producaoTotal; private List<ItemControle> indicadores; private List<RegistroProducao> producoes;
        public long getUsuariosAtivos(){return usuariosAtivos;} public long getTreinamentosPendentes(){return treinamentosPendentes;}
        public long getEstoqueCritico(){return estoqueCritico;} public long getVerdes(){return verdes;}
        public long getAmarelos(){return amarelos;} public long getVermelhos(){return vermelhos;}
        public BigDecimal getProducaoTotal(){return producaoTotal;} public List<ItemControle> getIndicadores(){return indicadores;}
        public List<RegistroProducao> getProducoes(){return producoes;}
    }
    private final UsuarioRepository usuarios=new UsuarioRepository();
    private final TreinamentoRepository treinamentos=new TreinamentoRepository();
    private final EstoqueRepository estoque=new EstoqueRepository();
    private final IndicadorRepository indicadores=new IndicadorRepository();
    private final ProducaoRepository producao=new ProducaoRepository();
    public Resumo resumo() {
        return Transacao.executar(new Transacao.Operacao<Resumo>() { public Resumo executar() {
            Resumo r=new Resumo(); r.usuariosAtivos=usuarios.ativos(); r.treinamentosPendentes=treinamentos.pendentes();
            r.estoqueCritico=estoque.criticos(); r.verdes=indicadores.porStatus("VERDE"); r.amarelos=indicadores.porStatus("AMARELO");
            r.vermelhos=indicadores.porStatus("VERMELHO"); r.indicadores=indicadores.listar(); r.producaoTotal=producao.total();
            r.producoes=producao.recentes(); return r;
        }});
    }
}