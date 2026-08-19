package br.com.sideral.service;
import java.math.BigDecimal; import java.util.Date; import br.com.sideral.domain.ControlItem; import br.com.sideral.domain.TrafficColor; import br.com.sideral.repository.ControlItemRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service
public class TrafficLightService {
 @Autowired private ControlItemRepository controlItems;
 @Transactional public ControlItem registerReading(Long id, BigDecimal value) {
  ControlItem item=controlItems.findById(id); if(item==null) throw new IllegalArgumentException("Item de controle não localizado");
  item.setCurrentValue(value); item.setUpdatedAt(new Date()); item.setTrafficColor(evaluate(item)); return controlItems.save(item);
 }
 public TrafficColor evaluate(ControlItem item) {
  if(item.getCurrentValue()==null || item.getWarningLimit()==null || item.getCriticalLimit()==null)return TrafficColor.YELLOW;
  if(item.getCurrentValue().compareTo(item.getCriticalLimit())>=0)return TrafficColor.RED;
  if(item.getCurrentValue().compareTo(item.getWarningLimit())>=0)return TrafficColor.YELLOW;
  return TrafficColor.GREEN;
 }
}