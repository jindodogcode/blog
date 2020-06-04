package dev.mkennedy.blog.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.model.BlogUserDetails;
import dev.mkennedy.blog.repository.UserRepository;

@Service
public class BlogUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        return user
            .map(BlogUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
