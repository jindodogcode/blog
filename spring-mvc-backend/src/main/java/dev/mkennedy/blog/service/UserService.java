package dev.mkennedy.blog.service;

import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.model.NewUserForm;

public interface UserService {

    public User save(User user);

    public User saveUserAndSecurity(User user, UserSecurity security);

    public User saveNewUser(NewUserForm newUserForm); 

    public User findByUsername(String username);

    public void delete(User user);
}
