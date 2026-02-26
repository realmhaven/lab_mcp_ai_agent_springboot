package com.example.agent.tools;

import com.example.agent.mcp.McpHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubMcpToolsTest {

    @Mock
    McpHttpClient mcp;

    @Test
    void should_call_create_issue_mcp_tool() {
        // Arrange
        GitHubMcpTools tools = new GitHubMcpTools(mcp, "test-owner", "test-repo");

        Map<String, Object> expectedArgs = Map.of(
                "owner", "test-owner",
                "repo", "test-repo",
                "title", "Fix bug",
                "body", "The bug is fixed");

        when(mcp.callTool(eq("create_issue"), eq(expectedArgs)))
                .thenReturn(Mono.just(Map.of("url", "https://github.com/test/issue/1")));

        // Act
        String result = tools.createIssue("Fix bug", "The bug is fixed");

        // Assert
        assertThat(result).contains("https://github.com/test/issue/1");
        verify(mcp).callTool("create_issue", expectedArgs);
    }
}
