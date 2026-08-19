package br.com.sideral.infra;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import br.com.sideral.domain.*;
import br.com.sideral.repository.*;

@WebListener
public class DadosIniciaisListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        Transacao.executar(new Transacao.Operacao<Void>() { public Void executar() {
            UsuarioRepository usuarios=new UsuarioRepository();
            if (usuarios.total() > 0) return null;
            usuarios.salvar(new Usuario("Marina Santos", "1001", "marina.santos@sideral.com.br", "Gerente Industrial"));
            usuarios.salvar(new Usuario("Rafael Lima", "1002", "rafael.lima@sideral.com.br", "Supervisor de Produção"));
            TreinamentoRepository treinamentos=new TreinamentoRepository();
            treinamentos.salvar(new TreinamentoMotorista("Carlos Oliveira", "Operação de Carreta", dataComDias(115), "REGULAR"));
            treinamentos.salvar(new TreinamentoMotorista("João Ferreira", "Direção Defensiva", dataComDias(-6), "VENCIDO"));
            IndicadorRepository indicadores=new IndicadorRepository();
            indicadores.salvar(new ItemControle("Produção de ferro-gusa", "Alto-forno", new BigDecimal("1200"), new BigDecimal("1248")));
            indicadores.salvar(new ItemControle("Disponibilidade AF-02", "Manutenção", new BigDecimal("92"), new BigDecimal("88")));
            indicadores.salvar(new ItemControle("Consumo de coque", "Redução", new BigDecimal("480"), new BigDecimal("398")));
            indicadores.salvar(new ItemControle("Expedição programada", "Logística", new BigDecimal("900"), new BigDecimal("850")));
            ProducaoRepository producao=new ProducaoRepository();
            producao.salvar(new RegistroProducao(new Date(), "Alto-forno 01", new BigDecimal("1248"), new BigDecimal("96.8")));
            EstoqueRepository estoque=new EstoqueRepository();
            estoque.salvar(new ItemEstoque("Coque metalúrgico", "Insumo", new BigDecimal("680"), new BigDecimal("700")));
            estoque.salvar(new ItemEstoque("Luva térmica", "EPI", new BigDecimal("1200"), new BigDecimal("400")));
            return null;
        }});
    }
    public void contextDestroyed(ServletContextEvent event) { HibernateUtil.shutdown(); }
    private static Date dataComDias(int dias) { Calendar c=Calendar.getInstance(); c.add(Calendar.DAY_OF_YEAR, dias); return c.getTime(); }
}