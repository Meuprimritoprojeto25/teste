package br.com.sideral.domain;
import java.math.BigDecimal; import java.util.Date; import javax.persistence.*;
@Entity
public class ControlItem {
 @Id @GeneratedValue private Long id; @Column(nullable=false) private String code; private String name; private String area; private String owner;
 private BigDecimal target; private BigDecimal currentValue; private BigDecimal warningLimit; private BigDecimal criticalLimit;
 @Enumerated(EnumType.STRING) private TrafficColor trafficColor; @Temporal(TemporalType.TIMESTAMP) private Date updatedAt;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getCode(){return code;} public void setCode(String v){code=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getArea(){return area;} public void setArea(String v){area=v;} public String getOwner(){return owner;} public void setOwner(String v){owner=v;} public BigDecimal getTarget(){return target;} public void setTarget(BigDecimal v){target=v;} public BigDecimal getCurrentValue(){return currentValue;} public void setCurrentValue(BigDecimal v){currentValue=v;} public BigDecimal getWarningLimit(){return warningLimit;} public void setWarningLimit(BigDecimal v){warningLimit=v;} public BigDecimal getCriticalLimit(){return criticalLimit;} public void setCriticalLimit(BigDecimal v){criticalLimit=v;} public TrafficColor getTrafficColor(){return trafficColor;} public void setTrafficColor(TrafficColor v){trafficColor=v;} public Date getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Date v){updatedAt=v;}
}