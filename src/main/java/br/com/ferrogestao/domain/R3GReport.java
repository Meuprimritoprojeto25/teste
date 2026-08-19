package br.com.ferrogestao.domain;

import br.com.ferrogestao.domain.enums.RecordStatus;
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
@Table(name = "r3g_report")
public class R3GReport extends BaseEntity {
    private String title;
    private String area;
    private Date referenceDate;
    private String resultSummary;
    private String gaps;
    private String gains;
    private String nextSteps;
    private User author;
    private RecordStatus status = RecordStatus.DRAFT;

    @Column(nullable = false, length = 160)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    @Column(nullable = false, length = 80)
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getReferenceDate() { return referenceDate; }
    public void setReferenceDate(Date referenceDate) { this.referenceDate = referenceDate; }
    @Column(length = 3000)
    public String getResultSummary() { return resultSummary; }
    public void setResultSummary(String resultSummary) { this.resultSummary = resultSummary; }
    @Column(length = 3000)
    public String getGaps() { return gaps; }
    public void setGaps(String gaps) { this.gaps = gaps; }
    @Column(length = 3000)
    public String getGains() { return gains; }
    public void setGains(String gains) { this.gains = gains; }
    @Column(length = 3000)
    public String getNextSteps() { return nextSteps; }
    public void setNextSteps(String nextSteps) { this.nextSteps = nextSteps; }
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "author_id")
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    public RecordStatus getStatus() { return status; }
    public void setStatus(RecordStatus status) { this.status = status; }
}