package br.com.sideral.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "itens_estoque")
public class ItemEstoque implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=100) private String descricao;
    @Column(nullable=false, length=40) private String categoria;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal quantidade;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal minimo;
    public ItemEstoque() { }
    public ItemEstoque(String descricao, String categoria, BigDecimal quantidade, BigDecimal minimo) {
        this.descricao=descricao;this.categoria=categoria;this.quantidade=quantidade;this.minimo=minimo;
    }
    public Long getId(){return id;} public String getDescricao(){return descricao;} public String getCategoria(){return categoria;}
    public BigDecimal getQuantidade(){return quantidade;} public BigDecimal getMinimo(){return minimo;}
    public boolean isCritico(){return quantidade.compareTo(minimo)<0;}
}