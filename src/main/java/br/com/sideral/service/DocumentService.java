package br.com.sideral.service;
import java.util.Date; import java.util.List; import br.com.sideral.domain.DocumentRecord; import br.com.sideral.repository.DocumentRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class DocumentService {
 @Autowired private DocumentRepository repository;
 @Transactional(readOnly=true) public List<DocumentRecord> list(){return repository.findAll();}
 @Transactional(readOnly=true) public DocumentRecord get(Long id){return repository.findById(id);}
 @Transactional public DocumentRecord save(DocumentRecord document){document.setChangedAt(new Date());if(document.getRevision()==null||document.getRevision().length()==0)document.setRevision("1.0");return repository.save(document);}
}