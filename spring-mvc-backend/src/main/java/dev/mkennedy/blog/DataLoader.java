package dev.mkennedy.blog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;
import dev.mkennedy.blog.repository.PostRepository;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.service.UserTransactionService;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private UserRepository userRepo;
    private UserTransactionService userTransactionService;
    private PostRepository postRepo;

    public DataLoader(
        UserRepository userRepo,
        UserTransactionService userTransactionService,
        PostRepository postRepo
    ) {
        this.userRepo = userRepo;
        this.userTransactionService = userTransactionService;
        this.postRepo = postRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing users");

        Optional<User> dogOpt = userRepo.findByUsername("dog");
        User dog = dogOpt.orElseGet(() -> {
            UserSecurity s = new UserSecurity("dogdog", Role.ROLE_USER);
            User u = new User("dog@dog.com", "dog", "Dog", "Doggerton");
            u = userTransactionService.saveUserAndSecurity(u, s);
            u = userRepo.findById(u.getId()).get();
            s = u.getSecurity();

            logPersist("User", u);
            logPersist("User Security", s);
            return u;
        });

        Optional<User> catOpt = userRepo.findByUsername("cat");
        User cat = catOpt.orElseGet(() -> {
            UserSecurity s = new UserSecurity("cat", Role.ROLE_USER);
            User u =  new User("cat@cat.gov", "cat", "Cat", "De Catsville");
            u = userTransactionService.saveUserAndSecurity(u, s);
            u = userRepo.findById(u.getId()).get();
            s = u.getSecurity();

            logPersist("User", u);
            logPersist("User Security", s);
            return u;
        });

        log.info("Initializing posts");

        List<Post> posts = new ArrayList<>();
        postRepo.findByUser(dog).forEach(posts::add);
        if (posts.isEmpty()) {
            Arrays.asList(
                new Post("I had a great day", "it was really good", dog),
                new Post("I like to lick myself", "It's not my fault, I'm a dog.", dog)
            );
            postRepo.saveAll(posts).forEach(posts::add);

            log.info("Posts created: ");
            posts.forEach(System.out::println);
        }

    }

    private void logPersist(String itemType, Object item) {
        log.info(itemType + " created:\n" + item);
    }
}
