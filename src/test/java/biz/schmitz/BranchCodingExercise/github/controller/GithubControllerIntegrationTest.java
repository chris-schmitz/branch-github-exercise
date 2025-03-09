package biz.schmitz.BranchCodingExercise.github.controller;

import biz.schmitz.BranchCodingExercise.github.api.GithubFeignClient;
import biz.schmitz.BranchCodingExercise.github.api.GithubRepoMetadata;
import biz.schmitz.BranchCodingExercise.github.api.GithubUser;
import biz.schmitz.BranchCodingExercise.github.domain.GithubUserSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class GithubControllerIntegrationTest {

    @MockitoBean
    GithubFeignClient githubFeignClient;

    @Autowired
    GithubController controller;

    @Test
    public void getUserSummary_canGetSummary() {
        var username = "octocat";
        var user = new GithubUser(username, "", "", "Octocat", "", "", "");
        var userRepos = List.of(
                new GithubRepoMetadata("repo1", "http://github.com/octocat/repo1"),
                new GithubRepoMetadata("repo2", "http://github.com/octocat/repo2")
        );
        when(githubFeignClient.getUserData(username))
                .thenReturn(user);
        when(githubFeignClient.getUserRepoMetadata(username))
                .thenReturn(userRepos);
        var expected = new GithubUserSummary(
                user.userName(),
                user.displayName(),
                user.avatar(),
                user.geoLocation(),
                user.email(),
                user.url(),
                user.createdAt(),
                userRepos
        );

        var actual = controller.getUserSummary(username);

        assertEquals(expected, actual);
    }
}