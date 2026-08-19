package br.com.sideral.service;
import java.util.Date; import java.util.List; import br.com.sideral.domain.ControlItem; import br.com.sideral.domain.FollowUp; import br.com.sideral.repository.ControlItemRepository; import br.com.sideral.repository.FollowUpRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class FollowUpService {
 @Autowired private FollowUpRepository repository; @Autowired private ControlItemRepository controlItems;
 @Transactional(readOnly=true) public List<FollowUp> list(Long id){return repository.findByControlItem(id);}
 @Transactional public FollowUp register(Long controlItemId,String note,String author){ControlItem item=controlItems.findById(controlItemId); if(item==null)throw new IllegalArgumentException("Item não localizado"); FollowUp f=new FollowUp(); f.setControlItem(item);f.setNote(note);f.setAuthor(author);f.setRegisteredAt(new Date());return repository.save(f);}
}