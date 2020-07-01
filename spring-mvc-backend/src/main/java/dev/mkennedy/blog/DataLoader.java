package dev.mkennedy.blog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
import dev.mkennedy.blog.service.PostService;
import dev.mkennedy.blog.service.ReplyService;
import dev.mkennedy.blog.service.UserService;

@Component
@Profile({ "dev" })
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Random rand = new Random();
    private List<String> texts = Arrays.asList(
        "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vestibulum consectetur semper. Ut finibus arcu auctor nulla posuere, non elementum nisl blandit. Aliquam erat volutpat. Curabitur convallis odio quis odio interdum, a efficitur est interdum. Duis metus libero, accumsan eget mi sed, hendrerit egestas odio. Mauris id rutrum ex. Quisque pellentesque hendrerit mauris, sit amet consequat odio dapibus quis. Suspendisse mattis lorem placerat maximus tempus. Quisque euismod varius condimentum. Nunc at consequat lectus. Suspendisse eleifend dapibus tellus ac sodales. Sed laoreet ligula at eros rhoncus ullamcorper. Nullam tristique nulla quis dui blandit, eu placerat sapien feugiat.</p>",
        "<p>Suspendisse vehicula nibh sed velit porttitor feugiat. Aliquam pulvinar mattis ligula, at tincidunt dui. Aenean nec tincidunt eros, vel pulvinar metus. Sed id tortor id metus mollis ultricies sit amet vel massa. Suspendisse ut accumsan dui. Proin ut tincidunt elit. Aliquam eget pharetra ligula. Integer blandit sagittis ligula, eget volutpat lorem mattis viverra.</p>",
        "<p>Integer varius lorem sit amet tellus eleifend, eu tristique felis venenatis. Nunc ullamcorper faucibus faucibus. Vivamus id arcu lectus. Cras sed ullamcorper odio, in cursus lorem. Sed commodo sed tellus id placerat. Aenean lacus nisl, laoreet sed sapien at, pulvinar interdum risus. Donec dictum, nunc vel tempus consequat, felis lectus finibus sem, at tempus nibh orci quis arcu.</p>",
        "<p>Vivamus nec arcu ullamcorper, aliquet mi ac, interdum odio. Curabitur at orci eu velit pharetra semper. Integer sed mauris id augue fringilla semper. Etiam metus ligula, euismod sit amet laoreet a, fermentum eu ante. Maecenas vitae lacus in metus lobortis dictum convallis id neque. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus id aliquam nunc. Nullam convallis luctus rhoncus. Quisque lacinia, eros nec blandit malesuada, tortor dolor ullamcorper massa, sed malesuada ipsum sapien ac lacus. Integer interdum, purus egestas porta varius, massa enim euismod magna, vitae vulputate nibh elit euismod turpis. Sed condimentum arcu molestie risus rutrum porta. Mauris pellentesque rutrum mauris, suscipit tempor est pretium eu. Cras venenatis scelerisque nunc ut molestie. Vestibulum lobortis, ante id efficitur blandit, dui dolor molestie erat, eu interdum sem magna at urna. Nulla facilisi.</p>",
        "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse imperdiet nisi a ante lobortis pulvinar. Donec eu tempor velit. Nullam eget mauris vitae nulla dictum suscipit. Nullam ornare pellentesque sodales. Nulla rutrum mollis risus, nec ullamcorper sem vehicula et. Phasellus rhoncus hendrerit porttitor. Vivamus at velit quis urna lacinia cursus eget sit amet nisl. Vestibulum malesuada pulvinar orci non tincidunt. Fusce bibendum turpis vitae rutrum rhoncus. Curabitur urna nisl, tincidunt sed dignissim non, ultricies id dui. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.</p>",
        "<p>Quisque ac dui mollis, luctus eros rhoncus, tincidunt quam. Cras id massa et purus commodo dignissim. Quisque sed pellentesque massa. Sed pharetra nisl non enim tincidunt, at rutrum felis mattis. Cras lobortis dolor semper, sagittis tellus eu, porta velit. Vivamus a odio ante. Nam scelerisque finibus felis. Nam lacinia sit amet felis et pharetra. Sed facilisis lacinia justo, ac scelerisque turpis pretium vitae. Sed ac sapien scelerisque, maximus dui posuere, consequat est. Phasellus nec risus rutrum, tempor libero a, accumsan velit. Quisque at ultrices sapien.</p>",
        "<p>Duis et neque interdum, tristique metus tristique, faucibus felis. Donec dignissim sagittis sem mollis commodo. Proin tempus varius blandit. Praesent vitae nisi id lectus euismod consequat a id felis. Sed ornare, justo a tincidunt fringilla, nibh diam lobortis quam, at commodo odio metus pretium erat. Duis molestie eros eget ex egestas, vitae pharetra diam cursus. Fusce tristique, nibh imperdiet venenatis rutrum, eros massa pharetra urna, eget facilisis erat ante non lectus. Sed ultricies lorem quis nibh semper, vel volutpat mi rutrum. Cras euismod erat orci, vel consequat mauris rutrum eget. Vestibulum eu diam eu ipsum vehicula sodales eget in diam. Vivamus ex dui, tincidunt eget mauris vitae, aliquam dictum tellus. Cras eget sem non orci molestie tempor.</p>",
        "<p>Pellentesque porta, nisl id sodales euismod, diam neque commodo tellus, non porttitor arcu velit sed ipsum. Curabitur eget faucibus ex. Cras egestas, est nec euismod consectetur, odio erat maximus dolor, nec tincidunt ex erat ut ex. Vivamus ultricies vestibulum ultrices. Nunc maximus, orci nec varius laoreet, ex ex viverra nibh, sit amet condimentum nunc magna non ligula. Sed quis lorem ullamcorper, dictum dui facilisis, dignissim eros. Duis vestibulum odio arcu, viverra porttitor nisl eleifend quis. Maecenas risus ligula, malesuada vitae condimentum sodales, porttitor in eros. Proin congue posuere bibendum.</p>",
        "<p>Vivamus semper fringilla nunc. Maecenas mollis, est in porta facilisis, augue lectus convallis velit, eu dapibus magna tortor a lacus. Sed porttitor ipsum non egestas venenatis. Nullam congue odio a sem pulvinar semper. Nunc ac fringilla ante. Duis eget metus massa. Suspendisse id diam vitae arcu tristique dapibus id nec magna.</p>",
        "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin id velit ac libero dictum tristique non eu lectus. Cras eget eros urna. Aenean rutrum ac velit ac fermentum. Mauris porta augue ligula, vitae consequat tortor vehicula at. Morbi pharetra, lorem at dignissim imperdiet, tellus lectus tristique nunc, eget accumsan tellus orci ut nunc. In aliquam gravida dui, vitae blandit urna maximus et. Vivamus ut congue sem, viverra facilisis velit. Aenean et cursus lorem. Duis pellentesque tellus non tincidunt sodales. Sed aliquam metus vitae elit faucibus, sed porta nulla porta. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.</p>"
    );

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing users");

        List<User> users = Arrays.asList(
                new User("dog@dog.com", "dog", "Dog", "Doggerton", "A dog"),
                new User("cat@example.com", "cat", "Cat", "de Catsville", "A cat"),
                new User("otter@example.com", "otter", "Otter", "von Otterwitz", "An otter"),
                new User("sloth@example.com", "sloth", "Sloth", "Slothington", "A sloth")
        );
        users.stream()
            .map(user -> userService.saveUserAndSecurity(
                user, new UserSecurity(passwordEncoder.encode("password"), Role.ROLE_USER)))
            .forEach(user -> logPersist("User", user));

        log.info("Initializing posts");

        List<Post> allPosts = new ArrayList<>();

        User dog = userService.findByUsername("dog");
        allPosts.addAll(Arrays.asList(
                new Post("Dog Day Afternoon", lorumIpsumGenerator(5), dog),
                new Post("Another Day Full of Scratches and Treats", lorumIpsumGenerator(5), dog),
                new Post("I Chased a Rabbit!!!", lorumIpsumGenerator(5), dog)
        ));

        User cat = userService.findByUsername("cat");
        allPosts.addAll(Arrays.asList(
                new Post("Cataphract: A cat focused Strategikon", lorumIpsumGenerator(5), cat),
                new Post("Yarn is Great!", lorumIpsumGenerator(5), cat),
                new Post("No Human, No Cry", lorumIpsumGenerator(5), cat)
        ));

        User otter = userService.findByUsername("otter");
        allPosts.addAll(Arrays.asList(
                new Post("Ottermas", lorumIpsumGenerator(5), otter),
                new Post("ON SWIMMING", lorumIpsumGenerator(5), otter),
                new Post("Zen and the Art of Whisker Maintenance", lorumIpsumGenerator(5), otter)
        ));

        User sloth = userService.findByUsername("sloth");
        allPosts.addAll(Arrays.asList(
                new Post("Life of a Sloth", lorumIpsumGenerator(5), sloth),
                new Post("Hangin' Out", lorumIpsumGenerator(5), sloth),
                new Post("Tree Life", lorumIpsumGenerator(5), sloth)
        ));

        Collections.shuffle(allPosts);
        allPosts = allPosts.stream()
            .map(postService::save)
            .peek(post -> logPersist("Post", post))
            .collect(Collectors.toList());

        log.info("Initializing replies");

        List<Reply> allReplies = Arrays.asList(
                new Reply(lorumIpsumGenerator(2), dog),
                new Reply(lorumIpsumGenerator(2), dog),
                new Reply(lorumIpsumGenerator(2), dog),
                new Reply(lorumIpsumGenerator(2), dog),
                new Reply(lorumIpsumGenerator(2), cat),
                new Reply(lorumIpsumGenerator(2), cat),
                new Reply(lorumIpsumGenerator(2), cat),
                new Reply(lorumIpsumGenerator(2), cat),
                new Reply(lorumIpsumGenerator(2), otter),
                new Reply(lorumIpsumGenerator(2), otter),
                new Reply(lorumIpsumGenerator(2), otter),
                new Reply(lorumIpsumGenerator(2), otter),
                new Reply(lorumIpsumGenerator(2), sloth),
                new Reply(lorumIpsumGenerator(2), sloth),
                new Reply(lorumIpsumGenerator(2), sloth),
                new Reply(lorumIpsumGenerator(2), sloth)
        );
        for (Reply reply : allReplies) {
            reply.setPost(allPosts.get(rand.nextInt(allPosts.size())));
        }
        allReplies = allReplies.stream()
            .map(replyService::save)
            .peek(reply -> logPersist("Reply", reply))
            .collect(Collectors.toList());
    }

    private void logPersist(String itemType, Object item) {
        log.info('\n' + itemType + " created:\n" + item);
    }

    private String lorumIpsumGenerator(int num) {
        // generate 1-num paragraphs from texts list
        int numParagraphs = rand.nextInt(num) + 1;
        StringBuilder loremIpsum = new StringBuilder();
        for (int i = 0; i <= numParagraphs; ++i) {
            loremIpsum.append(texts.get(rand.nextInt(texts.size())));
        }

        return loremIpsum.toString(); 
    }
}
