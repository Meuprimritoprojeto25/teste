package br.com.ferrogestao.repository.hibernate;

import br.com.ferrogestao.domain.DocumentRevision;
import br.com.ferrogestao.domain.ManagedDocument;
import br.com.ferrogestao.repository.AbstractHibernateRepository;
import br.com.ferrogestao.repository.DocumentRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateDocumentRepository extends AbstractHibernateRepository<ManagedDocument> implements DocumentRepository {
    public HibernateDocumentRepository() { super(ManagedDocument.class); }
    public void saveRevision(DocumentRevision revision) { session().save(revision); }
    @SuppressWarnings("unchecked")
    public List<DocumentRevision> revisions(Long documentId) {
        return session().createQuery("from DocumentRevision r where r.document.id=:id order by r.revisionNumber desc")
                .setParameter("id", documentId).list();
    }
}