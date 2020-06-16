package dev.mkennedy.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import dev.mkennedy.blog.PageableResponse;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.model.ApiError;
import dev.mkennedy.blog.model.NewPostForm;
import dev.mkennedy.blog.model.NewReplyForm;
import dev.mkennedy.blog.model.NewUserForm;
import dev.mkennedy.blog.model.UpdatePostForm;
import dev.mkennedy.blog.model.UpdateReplyForm;
import dev.mkennedy.blog.model.UpdateUserForm;
import java.net.URI;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "usertest", "test" })
public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;
    private URL base;

    @BeforeEach
    void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/v1");
    }

    @Test
    void addUserSuccessTest() throws Exception {
        NewUserForm userForm = new NewUserForm();
        userForm.setEmail("test@example.com");
        userForm.setUsername("testuser");
        userForm.setFirstName("Test");
        userForm.setLastName("User");
        userForm.setPassword("testpassword");
        URI uri = URI.create(base.toString() + "/users");
        RequestEntity<NewUserForm> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userForm);

        ResponseEntity<User> response = template.exchange(request, User.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        User body = response.getBody();
        assertThat(body.getEmail()).isEqualTo(userForm.getEmail());
        assertThat(body.getUsername()).isEqualTo(userForm.getUsername());
        assertThat(body.getFirstName()).isEqualTo(userForm.getFirstName());
        assertThat(body.getLastName()).isEqualTo(userForm.getLastName());
    }

    @Test
    void addUserValidationTest() throws Exception {
        NewUserForm userForm = new NewUserForm();
        userForm.setEmail("test");
        userForm.setUsername("tu");
        userForm.setFirstName("<script>code</script>");
        userForm.setPassword("pass");
        URI uri = URI.create(base.toString() + "/users");
        RequestEntity<NewUserForm> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userForm);

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Validation error");
        assertThat(body.getSubErrors().size()).isEqualTo(5);
    }

    @Test
    void addUserBadJSONTest() throws Exception {
        String badJSON = "this is malformed JSON";
        URI uri = URI.create(base.toString() + "/users");
        RequestEntity<String> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(badJSON);

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Malformed JSON request");
    }

    @Test
    void addUserNoRequestBody() throws Exception {
        URI uri = URI.create(base.toString() + "/users");
        RequestEntity<?> request = RequestEntity.post(uri)
            .body(null);

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Malformed JSON request");
    }

    @Test
    void getUserByUsernameSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/userone");
        RequestEntity<?> request = RequestEntity.get(uri).build();

        ResponseEntity<User> response = template.exchange(request, User.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        User body = response.getBody();
        assertThat(body.getEmail()).isEqualTo("userone@example.com");
        assertThat(body.getUsername()).isEqualTo("userone");
        assertThat(body.getFirstName()).isEqualTo("User");
        assertThat(body.getLastName()).isEqualTo("One");
    }

    @Test
    void getUserByUsernameBadUsernameTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/nobody");
        RequestEntity<?> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Not found");
    }

    @Test
    void updateUserByUsernameSuccessTest() throws Exception {
        UpdateUserForm userForm = new UpdateUserForm();
        userForm.setEmail("updated@example.com");
        userForm.setFirstName("Updated");
        userForm.setLastName("User");
        URI uri = URI.create(base.toString() + "/users/updateuser");
        RequestEntity<UpdateUserForm> request = RequestEntity.patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userForm);

        ResponseEntity<User> response = template.withBasicAuth("updateuser", "password")
            .exchange(request, User.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        User body = response.getBody();
        assertThat(body.getEmail()).isEqualTo(userForm.getEmail());
        assertThat(body.getFirstName()).isEqualTo(userForm.getFirstName());
        assertThat(body.getLastName()).isEqualTo(userForm.getLastName());
    }

    @Test
    void updateUserByUsernameValidationTest() throws Exception {
        UpdateUserForm userForm = new UpdateUserForm();
        userForm.setEmail("updateuser");
        userForm.setFirstName("1234updateuser");
        userForm.setLastName("<script>Some very scary code that will destroy everything<script>");
        URI uri = URI.create(base.toString() + "/users/updateuser");
        RequestEntity<UpdateUserForm> request = RequestEntity.patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userForm);

        ResponseEntity<ApiError> response = template.withBasicAuth("updateuser", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Validation error");
        assertThat(body.getSubErrors().size()).isEqualTo(4);
    }

    @Test
    void updateUserByUsernameUnauthorizedTest() throws Exception {
        UpdateUserForm userForm = new UpdateUserForm();
        userForm.setEmail("updateuser");
        userForm.setFirstName("1234updateuser");
        userForm.setLastName("<script>Some very scary code that will destroy everything<script>");
        URI uri = URI.create(base.toString() + "/users/updateuser");
        RequestEntity<UpdateUserForm> request = RequestEntity.patch(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(userForm);

        ResponseEntity<?> response = template.exchange(request, Object.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void deleteUserByUsernameSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/deleteuser");
        RequestEntity<?> request = RequestEntity.delete(uri).build();

        ResponseEntity<Void> response = template.withBasicAuth("deleteuser", "password")
            .exchange(request, Void.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteUserByUsernameNotLoggedInTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/deleteuser");
        RequestEntity<?> request = RequestEntity.delete(uri).build();

        ResponseEntity<Void> response = template.exchange(request, Void.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void deleteUserByUsernameUnauthorizedTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/deleteuser");
        RequestEntity<?> request = RequestEntity.delete(uri).build();

        ResponseEntity<Void> response = template.withBasicAuth("userone", "password")
            .exchange(request, Void.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void addUserPostSuccessTest() throws Exception {
        NewPostForm postForm = new NewPostForm();
        postForm.setTitle("Something");
        postForm.setContent("Something something something");
        URI uri = URI.create(base.toString() + "/users/usertwo/posts");
        RequestEntity<NewPostForm> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(postForm);

        ResponseEntity<Post> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, Post.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Post body = response.getBody();
        assertThat(body.getTitle()).isEqualTo(postForm.getTitle());
        assertThat(body.getContent()).isEqualTo(postForm.getContent());
    }

    @Test
    void addUserPostUnauthorizedTest() throws Exception {
        NewPostForm postForm = new NewPostForm();
        postForm.setTitle("Something");
        postForm.setContent("Something something something");
        URI uri = URI.create(base.toString() + "/users/usertwo/posts");
        RequestEntity<NewPostForm> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(postForm);

        ResponseEntity<?> response = template.exchange(request, Object.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void addUserPostValidationTest() throws Exception {
        NewPostForm postForm = new NewPostForm();
        postForm.setTitle("");
        URI uri = URI.create(base.toString() + "/users/usertwo/posts");
        RequestEntity<NewPostForm> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(postForm);

        ResponseEntity<ApiError> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Validation error");
    }

    @Test
    void addUserPostSanitizationTest() throws Exception {
        NewPostForm postForm = new NewPostForm();
        postForm.setTitle("<strong>title</strong>");
        postForm.setContent("<script>Some scary stuff</script><span>Content</span>");
        URI uri = URI.create(base.toString() + "/users/usertwo/posts");
        RequestEntity<NewPostForm> request = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(postForm);

        ResponseEntity<Post> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, Post.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Post body = response.getBody();
        assertThat(body.getTitle()).isEqualTo("<strong>title</strong>");
        assertThat(body.getContent()).isEqualTo("<span>Content</span>");
    }

    @Test
    void getUserPostsSuccess() throws Exception {
        URI uri = URI.create(base.toString() + "/users/userone/posts");
        RequestEntity<Void> request = RequestEntity.get(uri).build();
        ParameterizedTypeReference<PageableResponse<Post>> type = 
            new ParameterizedTypeReference<PageableResponse<Post>>(){};

        
        ResponseEntity<PageableResponse<Post>> response = template.exchange(request, type);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageableResponse<Post> body = response.getBody();
        assertThat(body.isEmpty()).isEqualTo(false);
        assertThat(body.getTotalElements()).isEqualTo(2);
    }

    @Test
    void getUserPostsBadUsernameTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/useron/posts");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateUserPostSuccessTest() throws Exception {
        UpdatePostForm postForm = new UpdatePostForm();
        postForm.setTitle("Updated Title");
        postForm.setContent("Updated content.");
        URI uri = URI.create(base.toString() + "/users/userone/posts/1");
        RequestEntity<UpdatePostForm> request = RequestEntity.patch(uri).body(postForm);

        ResponseEntity<Post> response = template.withBasicAuth("userone", "password")
            .exchange(request, Post.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Post body = response.getBody();
        assertThat(body.getTitle()).isEqualTo(postForm.getTitle());
        assertThat(body.getContent()).isEqualTo(postForm.getContent());
    }

    @Test
    void updateUserPostValidationTest() throws Exception {
        UpdatePostForm postForm = new UpdatePostForm();
        postForm.setTitle("");
        URI uri = URI.create(base.toString() + "/users/userone/posts/1");
        RequestEntity<UpdatePostForm> request = RequestEntity.patch(uri).body(postForm);

        ResponseEntity<ApiError> response = template.withBasicAuth("userone", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Validation error");
    }

    @Test
    void updateUserPostSanizationTest() throws Exception {
        UpdatePostForm postForm = new UpdatePostForm();
        postForm.setTitle("<strong>Updated Title</strong>");
        postForm.setContent("<script>evil code</script><em>Updated content.</em>");
        URI uri = URI.create(base.toString() + "/users/userone/posts/1");
        RequestEntity<UpdatePostForm> request = RequestEntity.patch(uri).body(postForm);

        ResponseEntity<Post> response = template.withBasicAuth("userone", "password")
            .exchange(request, Post.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Post body = response.getBody();
        assertThat(body.getTitle()).isEqualTo("<strong>Updated Title</strong>");
        assertThat(body.getContent()).isEqualTo("<em>Updated content.</em>");
    }

    @Test
    void updateUserPostUnauthorizedTest() throws Exception {
        UpdatePostForm postForm = new UpdatePostForm();
        postForm.setTitle("Title");
        postForm.setContent("Content.");
        URI uri = URI.create(base.toString() + "/users/userone/posts/1");
        RequestEntity<UpdatePostForm> request = RequestEntity.patch(uri).body(postForm);

        ResponseEntity<Object> response = template.exchange(request, Object.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void addUserReplyToPostSuccessTest() throws Exception {
        NewReplyForm replyForm = new NewReplyForm();
        replyForm.setContent("A new reply.");
        replyForm.setPostId(1L);
        URI uri = URI.create(base.toString() + "/users/usertwo/replies");
        RequestEntity<NewReplyForm> request = RequestEntity.post(uri).body(replyForm);

        ResponseEntity<Reply> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, Reply.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Reply body = response.getBody();
        assertThat(body.getContent()).isEqualTo(replyForm.getContent());
    }

    @Test
    void addUserReplyToReplySuccessTest() throws Exception {
        NewReplyForm replyForm = new NewReplyForm();
        replyForm.setContent("A new reply.");
        replyForm.setPostId(1L);
        replyForm.setReplyId(3L);
        URI uri = URI.create(base.toString() + "/users/userone/replies");
        RequestEntity<NewReplyForm> request = RequestEntity.post(uri).body(replyForm);

        ResponseEntity<Reply> response = template.withBasicAuth("userone", "password")
            .exchange(request, Reply.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Reply body = response.getBody();
        assertThat(body.getContent()).isEqualTo(replyForm.getContent());
    }

    @Test
    void addUserReplyValidationTest() throws Exception {
        NewReplyForm replyForm = new NewReplyForm();
        replyForm.setContent("");
        replyForm.setPostId(1L);
        URI uri = URI.create(base.toString() + "/users/usertwo/replies");
        RequestEntity<NewReplyForm> request = RequestEntity.post(uri).body(replyForm);

        ResponseEntity<ApiError> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Validation error");
    }

    @Test
    void addUserReplyUnauthorizedTest() throws Exception {
        NewReplyForm replyForm = new NewReplyForm();
        replyForm.setContent("Something");
        replyForm.setPostId(1L);
        URI uri = URI.create(base.toString() + "/users/usertwo/replies");
        RequestEntity<NewReplyForm> request = RequestEntity.post(uri).body(replyForm);

        ResponseEntity<Object> response = template.exchange(request, Object.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getUserRepliesSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/usertwo/replies");
        RequestEntity<Void> request = RequestEntity.get(uri).build();
        ParameterizedTypeReference<PageableResponse<Reply>> type =
            new ParameterizedTypeReference<PageableResponse<Reply>>(){};

        ResponseEntity<PageableResponse<Reply>> response = template.exchange(request, type);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageableResponse<Reply> body = response.getBody();
        assertThat(body.isEmpty()).isEqualTo(false);
    }

    @Test
    void getUserRepliesBadUsernameTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/userto/replies");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Not found");
    }

    @Test
    void updateUserReplySuccessTest() throws Exception {
        UpdateReplyForm replyForm = new UpdateReplyForm();
        replyForm.setContent("Updated content.");
        URI uri = URI.create(base.toString() + "/users/usertwo/replies/3");
        RequestEntity<UpdateReplyForm> request = RequestEntity.patch(uri).body(replyForm);

        ResponseEntity<Reply> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, Reply.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Reply body = response.getBody();
        assertThat(body.getContent()).isEqualTo(replyForm.getContent());
    }

    @Test
    void updateUserReplyBadUsernameTest() throws Exception {
        UpdateReplyForm replyForm = new UpdateReplyForm();
        replyForm.setContent("Updated content.");
        URI uri = URI.create(base.toString() + "/users/usertw/replies/3");
        RequestEntity<UpdateReplyForm> request = RequestEntity.patch(uri).body(replyForm);

        ResponseEntity<Object> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, Object.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void updateUserReplyBadReplyIdTest() throws Exception {
        UpdateReplyForm replyForm = new UpdateReplyForm();
        replyForm.setContent("Updated content.");
        URI uri = URI.create(base.toString() + "/users/usertwo/replies/1010");
        RequestEntity<UpdateReplyForm> request = RequestEntity.patch(uri).body(replyForm);

        ResponseEntity<ApiError> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Not found");
    }

    @Test
    void updateUserReplyValidationTest() throws Exception {
        UpdateReplyForm replyForm = new UpdateReplyForm();
        replyForm.setContent("");
        URI uri = URI.create(base.toString() + "/users/usertwo/replies/3");
        RequestEntity<UpdateReplyForm> request = RequestEntity.patch(uri).body(replyForm);

        ResponseEntity<ApiError> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Validation error");
    }

    @Test
    void updateUserReplySanitizationTest() throws Exception {
        UpdateReplyForm replyForm = new UpdateReplyForm();
        replyForm.setContent("<script>nasty stuff</script><li>Updated content.</li>");
        URI uri = URI.create(base.toString() + "/users/usertwo/replies/3");
        RequestEntity<UpdateReplyForm> request = RequestEntity.patch(uri).body(replyForm);

        ResponseEntity<Reply> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, Reply.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Reply body = response.getBody();
        assertThat(body.getContent()).isEqualTo("<li>Updated content.</li>");
    }

    private void logResponse(ResponseEntity<?> response, String methodName) throws Exception {
        StringBuilder logBuilder = new StringBuilder("\n\n");
        logBuilder.append("method: " + methodName + "\n\n");
        logBuilder.append("status: " + response.getStatusCode() + '\n');
        logBuilder.append("headers:\n");
        response.getHeaders().forEach((key, val) -> logBuilder.append('\t' + key + ": " + val + '\n'));
        logBuilder.append("body:\n");
        logBuilder.append(objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(response.getBody()));
        logBuilder.append('\n');
        log.info(logBuilder.toString());
    }
}
