package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.Driver;
import br.com.ferrogestao.domain.DriverTraining;
import br.com.ferrogestao.repository.TrainingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingService {
    @Autowired private TrainingRepository repository;
    @Transactional(readOnly = true)
    public List<DriverTraining> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public List<Driver> drivers() { return repository.findDrivers(); }
    @Transactional(readOnly = true)
    public List<DriverTraining> expiring() { return repository.findExpiring(60); }
    @Transactional
    public Driver saveDriver(Driver driver) { return repository.saveDriver(driver); }
    @Transactional
    public DriverTraining saveTraining(DriverTraining training) {
        if (training.getDriver() == null || training.getDriver().getId() == null) {
            throw new BusinessException("Selecione um motorista.");
        }
        training.setDriver(repository.findDriver(training.getDriver().getId()));
        if (training.getCompletionDate() != null && training.getExpirationDate() != null
                && training.getExpirationDate().before(training.getCompletionDate())) {
            throw new BusinessException("A validade não pode ser anterior à conclusão.");
        }
        training.setApproved(training.getScore() != null && training.getScore() >= 70d);
        return repository.save(training);
    }
}