package br.com.sideral.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "treinamentos_motorista")
public class TreinamentoMotorista implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 120) private String motorista;
    @Column(nullable = false, length = 80) private String curso;
    @Temporal(TemporalType.DATE) @Column(nullable = false) private Date validade;
    @Column(nullable = false, length = 20) private String situacao;
    public TreinamentoMotorista() { }
    public TreinamentoMotorista(String motorista, String curso, Date validade, String situacao) {
        this.motorista = motorista; this.curso = curso; this.validade = validade; this.situacao = situacao;
    }
    public Long getId() { return id; } public String getMotorista() { return motorista; }
    public String getCurso() { return curso; } public Date getValidade() { return validade; }
    public String getSituacao() { return situacao; } public void setSituacao(String situacao) { this.situacao = situacao; }
}