package br.com.sideral.domain;
import javax.persistence.*;
@Entity @Table(name="sg_user")
public class User {
 @Id @GeneratedValue private Long id;
 @Column(nullable=false) private String name;
 @Column(unique=true, nullable=false) private String login;
 private String email; private String role; private Boolean active = Boolean.TRUE;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getLogin(){return login;} public void setLogin(String v){login=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getRole(){return role;} public void setRole(String v){role=v;} public Boolean getActive(){return active;} public void setActive(Boolean v){active=v;}
}