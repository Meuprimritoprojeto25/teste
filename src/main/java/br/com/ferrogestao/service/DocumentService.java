package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.DocumentRevision;
import br.com.ferrogestao.domain.ManagedDocument;
import br.com.ferrogestao.domain.User;
import br.com.ferrogestao.repository.DocumentRepository;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {
    @Autowired private DocumentRepository repository;
    @Transactional(readOnly = true)
    public List<ManagedDocument> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public ManagedDocument get(Long id) { return repository.find(id); }
    @Transactional(readOnly = true)
    public List<DocumentRevision> revisions(Long id) { return repository.revisions(id); }

    @Transactional
    public ManagedDocument save(ManagedDocument incoming, String reason, User editor) {
        if (incoming.getTitle() == null || incoming.getTitle().trim().length() < 3) {
            throw new BusinessException("Informe um título válido.");
        }
        if (incoming.getContent() == null || incoming.getContent().trim().length() < 10) {
            throw new BusinessException("O documento precisa possuir conteúdo.");
        }
        if (incoming.getId() != null) {
            ManagedDocument current = repository.find(incoming.getId());
            DocumentRevision history = new DocumentRevision();
            history.setDocument(current);
            history.setRevisionNumber(current.getRevision());
            history.setContent(current.getContent());
            history.setChangeReason(reason == null ? "Edição do documento" : reason);
            history.setChangedBy(editor);
            repository.saveRevision(history);
            incoming.setRevision(current.getRevision() + 1);
        } else {
            incoming.setRevision(1);
        }
        return repository.save(incoming);
    }

    @Transactional(readOnly = true)
    public byte[] asWord(Long id) {
        ManagedDocument document = repository.find(id);
        if (document == null) { throw new BusinessException("Documento não encontrado."); }
        try {
            XWPFDocument word = new XWPFDocument();
            XWPFParagraph title = word.createParagraph();
            title.setStyle("Title");
            title.createRun().setText(document.getTitle());
            word.createParagraph().createRun().setText("Código: " + document.getCode() + " | Revisão: " + document.getRevision());
            String[] paragraphs = document.getContent().split("\\r?\\n");
            for (String text : paragraphs) { word.createParagraph().createRun().setText(text); }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            word.write(output);
            word.close();
            return output.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar documento Word", e); }
    }
}