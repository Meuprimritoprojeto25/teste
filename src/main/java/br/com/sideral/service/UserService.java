package br.com.sideral.service;
import java.util.List; import br.com.sideral.domain.User; import br.com.sideral.repository.UserRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class UserService {
 @Autowired private UserRepository repository;
 @Transactional(readOnly=true) public List<User> list(){return repository.findAll();}
 @Transactional public User save(User user){User existing=repository.findByLogin(user.getLogin()); if(existing!=null && !existing.getId().equals(user.getId()))throw new IllegalArgumentException("Login já cadastrado"); return repository.save(user);}
 @Transactional(readOnly=true) public User get(Long id){return repository.findById(id);}
}