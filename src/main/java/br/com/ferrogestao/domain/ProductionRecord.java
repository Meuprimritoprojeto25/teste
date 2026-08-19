package br.com.ferrogestao.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "production_record")
public class ProductionRecord extends BaseEntity {
    private Date productionDate;
    private String plant;
    private String furnace;
    private String shift;
    private Double producedTons;
    private Double oreTons;
    private Double scrapTons;
    private Double energyMwh;
    private Double qualityIndex;
    private Integer downtimeMinutes;
    private String notes;

    @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getProductionDate() { return productionDate; }
    public void setProductionDate(Date productionDate) { this.productionDate = productionDate; }
    @Column(nullable = false, length = 60)
    public String getPlant() { return plant; }
    public void setPlant(String plant) { this.plant = plant; }
    @Column(nullable = false, length = 40)
    public String getFurnace() { return furnace; }
    public void setFurnace(String furnace) { this.furnace = furnace; }
    @Column(nullable = false, length = 20)
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public Double getProducedTons() { return producedTons; }
    public void setProducedTons(Double producedTons) { this.producedTons = producedTons; }
    public Double getOreTons() { return oreTons; }
    public void setOreTons(Double oreTons) { this.oreTons = oreTons; }
    public Double getScrapTons() { return scrapTons; }
    public void setScrapTons(Double scrapTons) { this.scrapTons = scrapTons; }
    public Double getEnergyMwh() { return energyMwh; }
    public void setEnergyMwh(Double energyMwh) { this.energyMwh = energyMwh; }
    public Double getQualityIndex() { return qualityIndex; }
    public void setQualityIndex(Double qualityIndex) { this.qualityIndex = qualityIndex; }
    public Integer getDowntimeMinutes() { return downtimeMinutes; }
    public void setDowntimeMinutes(Integer downtimeMinutes) { this.downtimeMinutes = downtimeMinutes; }
    @Column(length = 1000)
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}