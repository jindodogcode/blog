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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.model.ApiError;
import dev.mkennedy.blog.model.NewPostForm;
import dev.mkennedy.blog.model.NewUserForm;
import dev.mkennedy.blog.model.UpdateUserForm;
import java.net.URI;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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

        ResponseEntity<User> response = template.withBasicAuth("usertwo", "password")
            .exchange(request, User.class);

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

        ResponseEntity<ApiError> response = template.withBasicAuth("userone", "password")
            .exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Unexpected error");
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
    void getUserPostsSuccess() throws Exception {
        URI uri = URI.create(base.toString() + "/users/userone/posts");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<Object> response = template.withBasicAuth("userone", "password")
            .exchange(request, Object.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);
        System.out.println("body type: " + response.getBody().getClass().getName());
        System.out.println("body type: " + response.getBody().getClass().getTypeName());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void logResponse(ResponseEntity<?> response, String methodName) throws Exception {
        log.info("\nmethod: {}", methodName);
        System.out.println("\nstatus: " + response.getStatusCode());
        System.out.println("headers:");
        response.getHeaders().forEach((key, val) -> System.out.println("\t" + key + ": " + val));
        System.out.println("body:");
        System.out.println(objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(response.getBody()));
    }
}
