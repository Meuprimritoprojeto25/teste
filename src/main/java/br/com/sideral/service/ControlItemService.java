package br.com.sideral.service;
import java.util.Date; import java.util.List; import br.com.sideral.domain.ControlItem; import br.com.sideral.domain.TrafficColor; import br.com.sideral.repository.ControlItemRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class ControlItemService {
 @Autowired private ControlItemRepository repository; @Autowired private TrafficLightService trafficLightService;
 @Transactional(readOnly=true) public List<ControlItem> list(){return repository.findAll();}
 @Transactional(readOnly=true) public ControlItem get(Long id){return repository.findById(id);}
 @Transactional public ControlItem save(ControlItem item){item.setUpdatedAt(new Date()); item.setTrafficColor(trafficLightService.evaluate(item)); return repository.save(item);}
 @Transactional(readOnly=true) public List<ControlItem> byColor(TrafficColor color){return repository.findByColor(color);}
}