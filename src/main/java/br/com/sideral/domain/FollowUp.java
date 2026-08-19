package br.com.sideral.domain;
import java.util.Date; import javax.persistence.*;
@Entity
public class FollowUp {
 @Id @GeneratedValue private Long id; @ManyToOne private ControlItem controlItem; @Temporal(TemporalType.TIMESTAMP) private Date registeredAt; @Lob private String note; private String author;
 public Long getId(){return id;} public void setId(Long v){id=v;} public ControlItem getControlItem(){return controlItem;} public void setControlItem(ControlItem v){controlItem=v;} public Date getRegisteredAt(){return registeredAt;} public void setRegisteredAt(Date v){registeredAt=v;} public String getNote(){return note;} public void setNote(String v){note=v;} public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
}