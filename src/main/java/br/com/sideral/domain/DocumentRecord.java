package br.com.sideral.domain;
import java.util.Date; import javax.persistence.*;
@Entity
public class DocumentRecord {
 @Id @GeneratedValue private Long id; private String title; private String type; private String revision; @Lob private String content; private String author; @Temporal(TemporalType.TIMESTAMP) private Date changedAt;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getType(){return type;} public void setType(String v){type=v;} public String getRevision(){return revision;} public void setRevision(String v){revision=v;} public String getContent(){return content;} public void setContent(String v){content=v;} public String getAuthor(){return author;} public void setAuthor(String v){author=v;} public Date getChangedAt(){return changedAt;} public void setChangedAt(Date v){changedAt=v;}
}