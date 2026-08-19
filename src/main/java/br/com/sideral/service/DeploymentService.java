package br.com.sideral.service;
import java.util.List; import br.com.sideral.domain.Deployment; import br.com.sideral.repository.ControlItemRepository; import br.com.sideral.repository.DeploymentRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class DeploymentService {
 @Autowired private DeploymentRepository repository; @Autowired private ControlItemRepository controlItems;
 @Transactional(readOnly=true) public List<Deployment> listOpen(){return repository.findOpen();}
 @Transactional public Deployment save(Deployment deployment){if(deployment.getControlItem()==null||deployment.getControlItem().getId()==null)throw new IllegalArgumentException("Item de controle é obrigatório");deployment.setControlItem(controlItems.findById(deployment.getControlItem().getId()));if(deployment.getStatus()==null)deployment.setStatus("ABERTO"); return repository.save(deployment);}
}