package biz.schmitz.BranchCodingExercise.github.controller;

import biz.schmitz.BranchCodingExercise.github.domain.GithubUserSummary;
import biz.schmitz.BranchCodingExercise.github.exceptions.GithubUserNotFoundException;
import biz.schmitz.BranchCodingExercise.github.service.GithubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = GithubController.class)
@EnableCaching
class GithubControllerWebTest {
    @MockitoBean
    GithubService githubService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getUserSummary_givenValidUsername_canRequestDetailsForUsername() throws Exception {
        var objectMapper = new ObjectMapper();
        var username = "octocat";
        var expected = new GithubUserSummary(
                username,
                "The Octocat",
                "https://avatars3.githubusercontent.com/u/583231?v=4",
                "San Francisco",
                "ocotcat@gihub.com",
                "https://github.com/octocat",
                "2011-01-25 18:44:36",
                Collections.emptyList()
        );
        when(githubService.getUserSummary(username))
                .thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/github/" + username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(expected))
                )
        ;
    }

    @Test
    public void getUserSummary_ifUserCannotBeFound_expectNotFound() throws Exception {
        var username = "someUnknownUsername";
        when(githubService.getUserSummary(username))
                .thenThrow(GithubUserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/github/" + username))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }
}
