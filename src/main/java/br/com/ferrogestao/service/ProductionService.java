package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.ProductionRecord;
import br.com.ferrogestao.repository.ProductionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductionService {
    @Autowired private ProductionRepository repository;
    @Transactional(readOnly = true)
    public List<ProductionRecord> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public List<Object[]> totalsByPlant() { return repository.totalsByPlant(); }
    @Transactional
    public ProductionRecord save(ProductionRecord record) {
        if (record.getProducedTons() == null || record.getProducedTons() < 0) {
            throw new BusinessException("A produção não pode ser negativa.");
        }
        double input = safe(record.getOreTons()) + safe(record.getScrapTons());
        if (input > 0 && record.getProducedTons() > input * 1.1) {
            throw new BusinessException("Produção incompatível com a carga metálica informada.");
        }
        return repository.save(record);
    }
    private double safe(Double value) { return value == null ? 0d : value; }
}