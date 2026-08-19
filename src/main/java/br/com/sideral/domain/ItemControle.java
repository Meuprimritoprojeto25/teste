package br.com.sideral.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "itens_controle")
public class ItemControle implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 120) private String nome;
    @Column(nullable = false, length = 60) private String area;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal meta;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal valorAtual;
    @Column(nullable = false, length = 12) private String status;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false) private Date atualizadoEm;
    public ItemControle() { }
    public ItemControle(String nome, String area, BigDecimal meta, BigDecimal valorAtual) {
        this.nome=nome; this.area=area; this.meta=meta; this.valorAtual=valorAtual; this.atualizadoEm=new Date(); recalcularStatus();
    }
    public void atualizar(BigDecimal valor) { valorAtual=valor; atualizadoEm=new Date(); recalcularStatus(); }
    private void recalcularStatus() {
        BigDecimal atencao = meta.multiply(new BigDecimal("0.90"));
        status = valorAtual.compareTo(meta) >= 0 ? "VERDE" : (valorAtual.compareTo(atencao) >= 0 ? "AMARELO" : "VERMELHO");
    }
    public Long getId(){return id;} public String getNome(){return nome;} public String getArea(){return area;}
    public BigDecimal getMeta(){return meta;} public BigDecimal getValorAtual(){return valorAtual;}
    public String getStatus(){return status;} public Date getAtualizadoEm(){return atualizadoEm;}
}