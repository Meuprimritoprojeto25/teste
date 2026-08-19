package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.R3GReport;
import br.com.ferrogestao.repository.R3GRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class R3GService {
    @Autowired private R3GRepository repository;
    @Transactional(readOnly = true)
    public List<R3GReport> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public R3GReport get(Long id) { return repository.find(id); }
    @Transactional
    public R3GReport save(R3GReport report) {
        if (report.getReferenceDate() == null) { report.setReferenceDate(new Date()); }
        if (report.getResultSummary() == null || report.getResultSummary().trim().length() < 10) {
            throw new BusinessException("O R3G deve conter uma análise dos resultados.");
        }
        return repository.save(report);
    }
}