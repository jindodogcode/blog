package dev.mkennedy.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;

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
import dev.mkennedy.blog.entity.Reply;
import dev.mkennedy.blog.model.ApiError;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "replytest", "test" })
public class ReplyControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ReplyControllerTest.class);

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;
    private URL base;

    @BeforeEach
    private void setup() throws Exception {
        base = new URL("http://localhost:" + port + "/api/v1");
    }

    @Test
    void getRepliesSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/replies");
        RequestEntity<Void> request = RequestEntity.get(uri).build();
        ParameterizedTypeReference<PageableResponse<Reply>> type =
            new ParameterizedTypeReference<>(){};

        ResponseEntity<PageableResponse<Reply>> response = template.exchange(request, type);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageableResponse<Reply> body = response.getBody();
        assertThat(body.isEmpty()).isEqualTo(false);
    }

    @Test
    void getReplyByIdSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/replies/3");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<Reply> response = template.exchange(request, Reply.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Reply body = response.getBody();
        assertThat(body.getId()).isEqualTo(3L);
    }

    @Test
    void getReplyByIdBadIdTest() throws Exception {
        URI uri = URI.create(base.toString() + "/replies/1000");
        RequestEntity<Void> request = RequestEntity.get(uri).build();

        ResponseEntity<ApiError> response = template.exchange(request, ApiError.class);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ApiError body = response.getBody();
        assertThat(body.getMessage()).isEqualTo("Not found");
    }

    @Test
    void getReplyRepliesSuccessTest() throws Exception {
        URI uri = URI.create(base.toString() + "/replies/3/replies");
        RequestEntity<Void> request = RequestEntity.get(uri).build();
        ParameterizedTypeReference<PageableResponse<Reply>> type =
            new ParameterizedTypeReference<>(){};

        ResponseEntity<PageableResponse<Reply>> response = template.exchange(request, type);

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logResponse(response, methodName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageableResponse<Reply> body = response.getBody();
        assertThat(body.isEmpty()).isEqualTo(false);
    }

    @Test
    void getReplyRepliesBadUsernameTest() throws Exception {
        URI uri = URI.create(base.toString() + "/replies/1000/replies");
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
