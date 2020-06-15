package dev.mkennedy.blog.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;
import dev.mkennedy.blog.model.NewUserForm;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.repository.UserSecurityRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserSecurityRepository userSecurityRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User saveUserAndSecurity(User user, UserSecurity security) {
        user.setSecurity(null);
        User saved = userRepo.save(user);
        security.setUser(user);
        saved.setSecurity(userSecurityRepo.save(security));

        return saved;
    }

    @Override
    @Transactional
    public User saveNewUser(NewUserForm newUserForm) {
        User user = newUserForm.toUser(encoder, Role.ROLE_USER);
        UserSecurity security = user.getSecurity();
        user.setSecurity(null);
        User saved = userRepo.save(user);
        security.setUser(saved);
        saved.setSecurity(userSecurityRepo.save(security));

        return saved;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Username: " + username + " not found"));
    }

    @Override
    @Transactional
    public void delete(User user) {
        UserSecurity security = user.getSecurity();
        userSecurityRepo.delete(security);
        userRepo.delete(user);
    }
}
