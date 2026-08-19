package br.com.sideral.domain;
import java.util.Date; import javax.persistence.*;
@Entity
public class Deployment {
 @Id @GeneratedValue private Long id; @ManyToOne private ControlItem controlItem; private String action; private String responsible; @Temporal(TemporalType.DATE) private Date dueDate; private String status; @Lob private String evidence;
 public Long getId(){return id;} public void setId(Long v){id=v;} public ControlItem getControlItem(){return controlItem;} public void setControlItem(ControlItem v){controlItem=v;} public String getAction(){return action;} public void setAction(String v){action=v;} public String getResponsible(){return responsible;} public void setResponsible(String v){responsible=v;} public Date getDueDate(){return dueDate;} public void setDueDate(Date v){dueDate=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public String getEvidence(){return evidence;} public void setEvidence(String v){evidence=v;}
}