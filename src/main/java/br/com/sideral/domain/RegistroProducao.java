package br.com.sideral.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "registros_producao")
public class RegistroProducao implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Temporal(TemporalType.DATE) @Column(nullable = false) private Date data;
    @Column(nullable = false, length = 60) private String altoForno;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal toneladas;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal qualidade;
    public RegistroProducao() { }
    public RegistroProducao(Date data, String altoForno, BigDecimal toneladas, BigDecimal qualidade) {
        this.data=data; this.altoForno=altoForno; this.toneladas=toneladas; this.qualidade=qualidade;
    }
    public Long getId(){return id;} public Date getData(){return data;} public String getAltoForno(){return altoForno;}
    public BigDecimal getToneladas(){return toneladas;} public BigDecimal getQualidade(){return qualidade;}
}