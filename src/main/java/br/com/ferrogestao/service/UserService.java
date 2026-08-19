package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.User;
import br.com.ferrogestao.repository.UserRepository;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired private UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public User get(Long id) { return repository.find(id); }
    @Transactional(readOnly = true)
    public long count() { return repository.count(); }

    @Transactional
    public User save(User user, String plainPassword) {
        User duplicate = repository.findByEmail(user.getEmail());
        if (duplicate != null && !duplicate.getId().equals(user.getId())) {
            throw new BusinessException("Já existe um usuário com este e-mail.");
        }
        if (plainPassword != null && plainPassword.trim().length() >= 6) {
            user.setPasswordHash(hash(plainPassword));
        } else if (user.getId() != null) {
            User current = repository.find(user.getId());
            user.setPasswordHash(current.getPasswordHash());
            if (user.getVersion() == null) { user.setVersion(current.getVersion()); }
        } else if (user.getId() == null) {
            throw new BusinessException("A senha inicial deve possuir ao menos 6 caracteres.");
        }
        if (user.getRole() == null) { user.setRole("ANALISTA"); }
        return repository.save(user);
    }

    public String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Hex.encodeHexString(digest.digest(value.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível proteger a senha", e);
        }
    }
}