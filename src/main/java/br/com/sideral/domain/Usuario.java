package br.com.sideral.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "matricula"))
public class Usuario implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 120) private String nome;
    @Column(nullable = false, length = 20) private String matricula;
    @Column(nullable = false, length = 100) private String email;
    @Column(nullable = false, length = 50) private String perfil;
    @Column(nullable = false) private boolean ativo;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false) private Date criadoEm;
    public Usuario() { }
    public Usuario(String nome, String matricula, String email, String perfil) {
        this.nome = nome; this.matricula = matricula; this.email = email; this.perfil = perfil;
        this.ativo = true; this.criadoEm = new Date();
    }
    public Long getId() { return id; } public String getNome() { return nome; }
    public String getMatricula() { return matricula; } public String getEmail() { return email; }
    public String getPerfil() { return perfil; } public boolean isAtivo() { return ativo; }
    public Date getCriadoEm() { return criadoEm; } public void setAtivo(boolean ativo) { this.ativo = ativo; }
}