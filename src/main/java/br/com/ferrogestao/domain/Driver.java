package br.com.ferrogestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver extends BaseEntity {
    private String name;
    private String registration;
    private String cpf;
    private String licenseNumber;
    private String licenseCategory;
    private boolean active = true;

    @Column(nullable = false, length = 120)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Column(nullable = false, unique = true, length = 30)
    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }
    @Column(length = 14)
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    @Column(length = 30)
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    @Column(length = 5)
    public String getLicenseCategory() { return licenseCategory; }
    public void setLicenseCategory(String licenseCategory) { this.licenseCategory = licenseCategory; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}