package br.com.sideral.domain;
import java.util.Date; import javax.persistence.*;
@Entity
public class R3GReport {
 @Id @GeneratedValue private Long id; private String title; private String period; private String area; @Lob private String analysis; @Lob private String decisions; @Temporal(TemporalType.TIMESTAMP) private Date generatedAt;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getPeriod(){return period;} public void setPeriod(String v){period=v;} public String getArea(){return area;} public void setArea(String v){area=v;} public String getAnalysis(){return analysis;} public void setAnalysis(String v){analysis=v;} public String getDecisions(){return decisions;} public void setDecisions(String v){decisions=v;} public Date getGeneratedAt(){return generatedAt;} public void setGeneratedAt(Date v){generatedAt=v;}
}