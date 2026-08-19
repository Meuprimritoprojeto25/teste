package br.com.ferrogestao.repository;

import br.com.ferrogestao.domain.DocumentRevision;
import br.com.ferrogestao.domain.ManagedDocument;
import java.util.List;

public interface DocumentRepository extends CrudRepository<ManagedDocument> {
    void saveRevision(DocumentRevision revision);
    List<DocumentRevision> revisions(Long documentId);
}