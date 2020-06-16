package dev.mkennedy.blog.controller;

import java.net.URI;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import dev.mkennedy.blog.PageableResponse;
import dev.mkennedy.blog.entity.Post;
import dev.mkennedy.blog.model.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "posttest", "test" })
public class PostControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PostControllerTest.class);

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;
    private URL base;

    @BeforeEach
    void setup() throws Exception {
        base = new URL("http://localhost:" + port + "/api/v1");
    }

    @Test
    void getPostsSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/posts?page=0&pagesize=20");
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
    void getPostByIdSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/posts/1");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<Post> response = template.exchange(request, Post.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Post body = response.getBody();
        assertThat(body.getId()).isEqualTo(1);
    }

    @Test
    void getPostByIdBadIdTest() throws Exception {
        URI uri = URI.create(base.toString() + "/posts/1000");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Not found");
        assertThat(body.getDebugMessage()).isEqualTo("Post with id: 1000 not found");
    }

    @Test
    void getPostRepliesTest() throws Exception {
        URI uri = URI.create(base.toString() + "/posts/1/replies");
        RequestEntity<Void> request = RequestEntity.get(uri).build();
        ParameterizedTypeReference<PageableResponse<Post>> type =
            new ParameterizedTypeReference<PageableResponse<Post>>(){};

        ResponseEntity<PageableResponse<Post>> response = template.exchange(request, type);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageableResponse<Post> body = response.getBody();
        assertThat(body.isEmpty()).isEqualTo(false);
        assertThat(body.getContent().get(0).getContent())
            .isEqualTo("Something something something");
    }

    @Test
    void getPostRepliesBadIdTest() throws Exception {
        URI uri = URI.create(base.toString() + "/posts/1000/replies");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Not found");
    }

    private void logResponse(ResponseEntity<?> response, String methodName) throws Exception {
        StringBuilder logBuilder = new StringBuilder("\n\n");
        logBuilder.append("method: " + methodName + "\n\n");
        logBuilder.append("status: " + response.getStatusCode() + '\n');
        logBuilder.append("headers:\n");
        response.getHeaders().forEach((key, val) -> logBuilder
                .append('\t' + key + ": " + val + '\n'));
        logBuilder.append("body:\n");
        logBuilder.append(objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(response.getBody()));
        logBuilder.append('\n');
        log.info(logBuilder.toString());
    }
}
