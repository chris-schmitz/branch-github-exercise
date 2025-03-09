package biz.schmitz.BranchCodingExercise.github.controller;

import biz.schmitz.BranchCodingExercise.github.api.GithubFeignClient;
import biz.schmitz.BranchCodingExercise.github.api.GithubRepoMetadata;
import biz.schmitz.BranchCodingExercise.github.api.GithubUser;
import biz.schmitz.BranchCodingExercise.github.domain.GithubUserSummary;
import biz.schmitz.BranchCodingExercise.github.exceptions.GithubUserNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class GithubControllerIntegrationTest {

    @MockitoBean
    GithubFeignClient githubFeignClient;

    @Autowired
    GithubController githubController;

    @Test
    public void getUserSummary_givenValidusername_canGetSummary() {
        var username = "octocat";
        var user = new GithubUser(username,
                "The Octocat",
                "https://avatars3.githubusercontent.com/u/583231?v=4",
                "San Francisco",
                "ocotcat@gihub.com",
                "https://github.com/octocat",
                "2011-01-25 18:44:36"
        );
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

        var actual = githubController.getUserSummary(username);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserSummary_ifInvalidUsername_returnsJsonErrorWhenUserNotFound() {
        var username = "invalidUsername";
        when(githubFeignClient.getUserData(username))
                .thenThrow(FeignException.NotFound.class);
        var expected = "No user found with username: '" + username + "'";

        var exception = assertThrows(GithubUserNotFoundException.class, () -> githubController.getUserSummary(username));
        assertEquals(expected, exception.getMessage());
    }
}