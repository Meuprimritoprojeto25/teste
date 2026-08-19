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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "item_follow_up")
public class FollowUp extends BaseEntity {
    private ControlItem item;
    private Date referenceDate;
    private Double actualValue;
    private TrafficStatus status;
    private String analysis;
    private String rootCause;
    private String actionPlan;
    private User reportedBy;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id", nullable = false)
    public ControlItem getItem() { return item; }
    public void setItem(ControlItem item) { this.item = item; }

    @Temporal(TemporalType.DATE) @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getReferenceDate() { return referenceDate; }
    public void setReferenceDate(Date referenceDate) { this.referenceDate = referenceDate; }

    @Column(nullable = false)
    public Double getActualValue() { return actualValue; }
    public void setActualValue(Double actualValue) { this.actualValue = actualValue; }

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 15)
    public TrafficStatus getStatus() { return status; }
    public void setStatus(TrafficStatus status) { this.status = status; }

    @Column(length = 1000)
    public String getAnalysis() { return analysis; }
    public void setAnalysis(String analysis) { this.analysis = analysis; }

    @Column(length = 1000)
    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) { this.rootCause = rootCause; }

    @Column(length = 1500)
    public String getActionPlan() { return actionPlan; }
    public void setActionPlan(String actionPlan) { this.actionPlan = actionPlan; }

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reported_by")
    public User getReportedBy() { return reportedBy; }
    public void setReportedBy(User reportedBy) { this.reportedBy = reportedBy; }
}