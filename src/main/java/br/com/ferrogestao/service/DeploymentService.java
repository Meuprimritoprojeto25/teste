package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.Deployment;
import br.com.ferrogestao.repository.DeploymentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeploymentService {
    @Autowired private DeploymentRepository repository;
    @Transactional(readOnly = true)
    public List<Deployment> list() { return repository.findAll(); }
    @Transactional
    public Deployment save(Deployment value) {
        if (value.getParentItem() == null || value.getChildItem() == null) {
            throw new BusinessException("Indicadores pai e filho são obrigatórios.");
        }
        if (value.getParentItem().getId().equals(value.getChildItem().getId())) {
            throw new BusinessException("Um indicador não pode ser desdobrado nele mesmo.");
        }
        if (value.getWeight() == null || value.getWeight() <= 0 || value.getWeight() > 100) {
            throw new BusinessException("O peso deve estar entre 0 e 100%.");
        }
        for (Deployment current : repository.findAll()) {
            if (current.getParentItem().getId().equals(value.getChildItem().getId())
                    && current.getChildItem().getId().equals(value.getParentItem().getId())) {
                throw new BusinessException("O vínculo criaria um ciclo direto no desdobramento.");
            }
        }
        return repository.save(value);
    }
}