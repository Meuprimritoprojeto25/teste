package br.com.ferrogestao.domain;

import br.com.ferrogestao.domain.enums.TrafficStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "kpi_snapshot")
public class KpiSnapshot extends BaseEntity {
    private ControlItem item;
    private Date referenceDate;
    private Double targetValue;
    private Double actualValue;
    private Double achievement;
    private TrafficStatus status;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id", nullable = false)
    public ControlItem getItem() { return item; }
    public void setItem(ControlItem item) { this.item = item; }
    @Temporal(TemporalType.DATE) @Column(nullable = false)
    public Date getReferenceDate() { return referenceDate; }
    public void setReferenceDate(Date referenceDate) { this.referenceDate = referenceDate; }
    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }
    public Double getActualValue() { return actualValue; }
    public void setActualValue(Double actualValue) { this.actualValue = actualValue; }
    public Double getAchievement() { return achievement; }
    public void setAchievement(Double achievement) { this.achievement = achievement; }
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 15)
    public TrafficStatus getStatus() { return status; }
    public void setStatus(TrafficStatus status) { this.status = status; }
}