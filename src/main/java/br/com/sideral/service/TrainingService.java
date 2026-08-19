package br.com.sideral.service;
import java.util.Date; import java.util.List; import br.com.sideral.domain.DriverTraining; import br.com.sideral.repository.DriverTrainingRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class TrainingService {
 @Autowired private DriverTrainingRepository repository;
 @Transactional(readOnly=true) public List<DriverTraining> list(){return repository.findAll();}
 @Transactional(readOnly=true) public List<DriverTraining> expired(){return repository.findExpired();}
 @Transactional public DriverTraining save(DriverTraining training){if(training.getExpiration()!=null && training.getExpiration().before(new Date()))training.setStatus("VENCIDO"); else if(training.getStatus()==null)training.setStatus("VIGENTE"); return repository.save(training);}
}