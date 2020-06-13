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
import dev.mkennedy.blog.entity.User;
import dev.mkennedy.blog.model.ApiError;
import dev.mkennedy.blog.model.NewUserForm;
import java.net.URI;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    
    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    private int port;
    private URL base;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/v1");
    }

    // private String newUserJson = "{" +
    //     "\"email\": \"test@example.com\", " +
    //     "\"username\": \"testuser\", " +
    //     "\"firstName\": \"Test\", " +
    //     "\"lastName\": \"User\"" +
    //     "}";

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

        logResponse(response);
        
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

        logResponse(response);

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

        logResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Malformed JSON request");
    }

    @Test
    void getByUsernameSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/dog");
        RequestEntity<?> request = RequestEntity.get(uri).build();

        ResponseEntity<User> response = template.withBasicAuth("dog", "dogdog")
            .exchange(request, User.class);

        logResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        User body = response.getBody();
        assertThat(body.getEmail()).isEqualTo("dog@dog.com");
        assertThat(body.getUsername()).isEqualTo("dog");
        assertThat(body.getFirstName()).isEqualTo("Dog");
        assertThat(body.getLastName()).isEqualTo("Doggerton");
    }

    @Test
    void getByUsernameBadUsernameTest() throws Exception {
        URI uri = URI.create(base.toString() + "/users/nobody");
        RequestEntity<?> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.withBasicAuth("dog", "dogdog")
            .exchange(request, ApiError.class);

        logResponse(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void logResponse(ResponseEntity<?> response) throws Exception {
        log.info("\nstatus: {}", response.getStatusCode());
        System.out.println("headers:");
        response.getHeaders().forEach((key, val) -> System.out.println("\t" + key + ": " + val));
        System.out.println("body:");
        System.out.println(objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(response.getBody()));
    }
}
