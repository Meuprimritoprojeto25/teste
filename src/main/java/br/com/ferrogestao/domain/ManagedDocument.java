package br.com.ferrogestao.domain;

import br.com.ferrogestao.domain.enums.RecordStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "managed_document")
public class ManagedDocument extends BaseEntity {
    private String code;
    private String title;
    private String category;
    private String content;
    private Integer revision = 1;
    private RecordStatus status = RecordStatus.DRAFT;
    private User owner;

    @Column(nullable = false, unique = true, length = 30)
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    @Column(nullable = false, length = 180)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    @Column(nullable = false, length = 60)
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    @Lob @Column(nullable = false)
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    @Column(nullable = false)
    public Integer getRevision() { return revision; }
    public void setRevision(Integer revision) { this.revision = revision; }
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    public RecordStatus getStatus() { return status; }
    public void setStatus(RecordStatus status) { this.status = status; }
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id")
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}