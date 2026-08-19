package br.com.ferrogestao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "document_revision")
public class DocumentRevision extends BaseEntity {
    private ManagedDocument document;
    private Integer revisionNumber;
    private String content;
    private String changeReason;
    private User changedBy;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "document_id", nullable = false)
    public ManagedDocument getDocument() { return document; }
    public void setDocument(ManagedDocument document) { this.document = document; }
    @Column(nullable = false)
    public Integer getRevisionNumber() { return revisionNumber; }
    public void setRevisionNumber(Integer revisionNumber) { this.revisionNumber = revisionNumber; }
    @Lob @Column(nullable = false)
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    @Column(length = 500)
    public String getChangeReason() { return changeReason; }
    public void setChangeReason(String changeReason) { this.changeReason = changeReason; }
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "changed_by")
    public User getChangedBy() { return changedBy; }
    public void setChangedBy(User changedBy) { this.changedBy = changedBy; }
}