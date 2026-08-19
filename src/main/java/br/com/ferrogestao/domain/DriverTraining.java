package br.com.ferrogestao.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "driver_training")
public class DriverTraining extends BaseEntity {
    private Driver driver;
    private String course;
    private String instructor;
    private Date completionDate;
    private Date expirationDate;
    private Integer workloadHours;
    private Double score;
    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "driver_id", nullable = false)
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
    @Column(nullable = false, length = 140)
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    @Column(length = 120)
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getCompletionDate() { return completionDate; }
    public void setCompletionDate(Date completionDate) { this.completionDate = completionDate; }
    @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getExpirationDate() { return expirationDate; }
    public void setExpirationDate(Date expirationDate) { this.expirationDate = expirationDate; }
    public Integer getWorkloadHours() { return workloadHours; }
    public void setWorkloadHours(Integer workloadHours) { this.workloadHours = workloadHours; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}