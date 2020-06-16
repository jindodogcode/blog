package dev.mkennedy.blog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.entity.UserSecurity;
import dev.mkennedy.blog.entity.UserSecurity.Role;
import dev.mkennedy.blog.repository.PostRepository;
import dev.mkennedy.blog.repository.ReplyRepository;
import dev.mkennedy.blog.repository.UserRepository;
import dev.mkennedy.blog.service.UserService;

@Component
@Profile({ "posttest", "usertest", "replytest" })
public class TestDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private ReplyRepository replyRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing users");

        List<User> users = Arrays.asList(
            new User("userone@example.com", "userone", "User", "One"),
            new User("usertwo@example.com", "usertwo", "User", "Two"),
            new User("deleteuser@example.com", "deleteuser", "Delete", "User"),
            new User("updateuser@example.com", "updateuser", "Update", "User")
        );

        users.stream().map(user -> userService.saveUserAndSecurity(
                    user, new UserSecurity(passwordEncoder.encode("password"), Role.ROLE_USER)))
            .forEach(user -> logPersist("User", user));
        User userOne = userRepo.findByUsername("userone").get();
        User userTwo = userRepo.findByUsername("usertwo").get();

        log.info("Initializing posts");

        List<Post> posts = Arrays.asList(
                new Post("I had a great day", "it was really good", userOne),
                new Post("Something", "Something something something.", userOne));
        postRepo.saveAll(posts).forEach(p -> logPersist("Post", p));

        log.info("Initializing Replies");

        Post userOnePost = postRepo.findAllByUser(userOne).iterator().next();
        Reply userTwoReply = new Reply("Something something something", userTwo);
        userTwoReply.setPost(userOnePost);
        userTwoReply = replyRepo.save(userTwoReply);
        logPersist("Reply", userTwoReply);
        Reply userOneReply = new Reply("Interesting", userOne);
        userOneReply.setPost(userOnePost);
        userOneReply.setReply(userTwoReply);
        userOneReply = replyRepo.save(userOneReply);
    }

    private void logPersist(String itemType, Object item) {
        log.info('\n' + itemType + " created:\n" + item);
    }
}
