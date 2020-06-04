package dev.mkennedy.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.repository.UserSecurityRepository;

@Service
@Transactional
public class UserTransactionService {

    private UserRepository userRepo;
    private UserSecurityRepository userSecurityRepo;

    public UserTransactionService(UserRepository userRepo, UserSecurityRepository userSecurityRepo) {
        this.userRepo = userRepo;
        this.userSecurityRepo = userSecurityRepo;
    }

    public User saveUserAndSecurity(User user, UserSecurity security) {
        User saved = userRepo.save(user);
        security.setUser(user);
        saved.setSecurity(userSecurityRepo.save(security));

        return saved;
    }
}
