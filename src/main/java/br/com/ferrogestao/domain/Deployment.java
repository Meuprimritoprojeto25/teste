package br.com.ferrogestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_deployment")
public class Deployment extends BaseEntity {
    private ControlItem parentItem;
    private ControlItem childItem;
    private Double weight;
    private String rationale;
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_item_id", nullable = false)
    public ControlItem getParentItem() { return parentItem; }
    public void setParentItem(ControlItem parentItem) { this.parentItem = parentItem; }

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "child_item_id", nullable = false)
    public ControlItem getChildItem() { return childItem; }
    public void setChildItem(ControlItem childItem) { this.childItem = childItem; }

    @Column(nullable = false)
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    @Column(length = 500)
    public String getRationale() { return rationale; }
    public void setRationale(String rationale) { this.rationale = rationale; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}