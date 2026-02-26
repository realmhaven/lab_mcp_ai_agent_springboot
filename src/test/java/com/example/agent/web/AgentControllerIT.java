package com.example.agent.web;

import com.example.agent.agent.BacklogAgent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentControllerIT {

    @Autowired
    WebTestClient web;

    @MockBean
    BacklogAgent backlogAgent;

    @Test
    void should_run_agent_and_return_response() {
        when(backlogAgent.handle(anyString())).thenReturn("Issue #42 created");

        String response = web.post()
                .uri("/api/run")
                .bodyValue("Create a bug fix issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assert response != null;
        assert response.contains("Issue #42 created");
    }
}
