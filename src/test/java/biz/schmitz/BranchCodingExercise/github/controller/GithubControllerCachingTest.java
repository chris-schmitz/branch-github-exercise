package biz.schmitz.BranchCodingExercise.github.controller;

import biz.schmitz.BranchCodingExercise.github.api.GithubFeignClient;
import biz.schmitz.BranchCodingExercise.github.api.GithubUser;
import biz.schmitz.BranchCodingExercise.github.service.GithubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableCaching
public class GithubControllerCachingTest {

    @MockitoSpyBean
    private GithubService mockedGithubService;

    @MockitoBean
    private GithubFeignClient githubFeignClient;

    @Autowired
    private GithubService githubService;

    @Test
    public void getUserSummary_resultsAreCached() {
        var username = "octocat";
        var user = new GithubUser(username, "", "", "", "", "", "");
        when(githubFeignClient.getUserData(username)).thenReturn(user);
        when(githubFeignClient.getUserRepoMetadata(username)).thenReturn(Collections.emptyList());

        var actual1 = githubService.getUserSummary(username);
        var actual2 = githubService.getUserSummary(username);

        verify(mockedGithubService, times(1)).getUserSummary(username);
        assertSame(actual1, actual2);
    }

}
