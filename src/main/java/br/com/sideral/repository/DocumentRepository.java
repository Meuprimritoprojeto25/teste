package br.com.sideral.repository;
import br.com.sideral.domain.DocumentRecord; import org.springframework.stereotype.Repository;
@Repository public class DocumentRepository extends HibernateRepository<DocumentRecord,Long> { public DocumentRepository(){super(DocumentRecord.class);} }