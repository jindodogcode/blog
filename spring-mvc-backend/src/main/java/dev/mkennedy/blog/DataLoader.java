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
import dev.mkennedy.blog.repository.PostRepository;
import dev.mkennedy.blog.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private UserRepository userRepo;
    private PostRepository postRepo;

    public DataLoader(UserRepository userRepo, PostRepository postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing users");

        Optional<User> frankieOpt = userRepo.findByUserName("frankie");
        User frankie = frankieOpt.orElseGet(() -> {
            User f = new User("frankie@frankmail.com", "frankie", "frankie", "Frankie", "Frankerton");
            f = userRepo.save(f);
            logPersist("User", f);
            return f;
        });

        Optional<User> kimOpt = userRepo.findByUserName("kim");
        User kim = kimOpt.orElseGet(() -> {
            User k =  new User("kim@kimmail.com", "kim", "kim", "Kim", "Karolyi");
            k = userRepo.save(k);
            logPersist("User", k);
            return k;
        });

        log.info("Initializing posts");

        List<Post> posts = new ArrayList<>();
        postRepo.findByUser(frankie).forEach(posts::add);
        if (posts.isEmpty()) {
            Arrays.asList(
                new Post("I had a great day", "it was really fucking good", frankie),
                new Post("I like to lick myself", "It's not my fault, my butt just is that great.", frankie)
            );
            postRepo.saveAll(posts).forEach(posts::add);

            log.info("Posts created: ");
            posts.forEach(System.out::println);
        }

    }

    private void logPersist(String itemType, Object item) {
        log.info(itemType + " created: " + item);
    }
}
