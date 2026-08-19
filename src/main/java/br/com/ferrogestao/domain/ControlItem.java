package br.com.ferrogestao.domain;

import br.com.ferrogestao.domain.enums.Direction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "control_item")
public class ControlItem extends BaseEntity {
    private String code;
    private String name;
    private String area;
    private String unit;
    private String periodicity;
    private Double target;
    private Double warningLimit;
    private Double lowerLimit;
    private Direction direction = Direction.HIGHER_IS_BETTER;
    private User owner;
    private boolean active = true;

    @NotNull @Size(min = 2, max = 30)
    @Column(nullable = false, unique = true, length = 30)
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    @NotNull @Size(min = 3, max = 140)
    @Column(nullable = false, length = 140)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(nullable = false, length = 80)
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    @Column(nullable = false, length = 20)
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    @Column(length = 20)
    public String getPeriodicity() { return periodicity; }
    public void setPeriodicity(String periodicity) { this.periodicity = periodicity; }

    @Column(nullable = false)
    public Double getTarget() { return target; }
    public void setTarget(Double target) { this.target = target; }

    @Column(nullable = false)
    public Double getWarningLimit() { return warningLimit; }
    public void setWarningLimit(Double warningLimit) { this.warningLimit = warningLimit; }

    public Double getLowerLimit() { return lowerLimit; }
    public void setLowerLimit(Double lowerLimit) { this.lowerLimit = lowerLimit; }

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30)
    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id")
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    @Column(nullable = false)
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}