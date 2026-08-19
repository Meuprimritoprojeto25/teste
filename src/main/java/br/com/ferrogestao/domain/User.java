package br.com.ferrogestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "fg_user", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User extends BaseEntity {
    private String name;
    private String email;
    private String registration;
    private String department;
    private String role;
    private String passwordHash;
    private boolean active = true;

    @NotNull @Size(min = 3, max = 120)
    @Column(nullable = false, length = 120)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @NotNull @Size(min = 5, max = 160)
    @Column(nullable = false, length = 160)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Column(length = 30)
    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }

    @Column(length = 80)
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Column(length = 40)
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Column(nullable = false, length = 64)
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    @Column(nullable = false)
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}