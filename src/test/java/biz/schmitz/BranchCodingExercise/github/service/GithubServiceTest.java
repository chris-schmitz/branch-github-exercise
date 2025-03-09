package biz.schmitz.BranchCodingExercise.github.service;

import biz.schmitz.BranchCodingExercise.github.api.GithubFeignClient;
import biz.schmitz.BranchCodingExercise.github.api.GithubRepoMetadata;
import biz.schmitz.BranchCodingExercise.github.api.GithubUser;
import biz.schmitz.BranchCodingExercise.github.domain.GithubUserSummary;
import biz.schmitz.BranchCodingExercise.github.exceptions.GithubUserNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Mock
    private GithubFeignClient githubFeignClient;

    @InjectMocks
    private GithubService githubService;

    @Test
    public void getUserSummary_givenValidUsernameForAUserWithRepos_expectSummary() {
        var username = "octocat";
        var user = new GithubUser(
                username,
                "Octocat",
                "https://avatars3.githubusercontent.com/u/583231?v=4",
                "San Francisco",
                "https://github.com/octocat ",
                null,
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

        var actual = githubService.getUserSummary(username);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserSummary_givenValidUsernameForAUserWithNoRepos_expectSummary() {
        // * It's not explicitly called out by the documentation, though it kind of hints at it, but
        // * if you have a user without any repos, the repo call will NOT return an error, it just returns
        // * an empty list.
        // * https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
        var username = "userWithNoRepos";
        var user = new GithubUser(username, "", "", "", "", "", "");
        when(githubFeignClient.getUserData(username))
                .thenReturn(user);
        when(githubFeignClient.getUserRepoMetadata(username))
                .thenReturn(Collections.emptyList());
        var expected = new GithubUserSummary(
                user.userName(),
                user.displayName(),
                user.avatar(),
                user.geoLocation(),
                user.email(),
                user.url(),
                user.createdAt(),
                Collections.emptyList()
        );

        var actual = githubService.getUserSummary(username);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserSummary_givenAnInvalidUsername_expectException() {
        var username = "invalidUser";
        when(githubFeignClient.getUserData(username))
                .thenThrow(FeignException.NotFound.class);

        assertThrows(GithubUserNotFoundException.class, () -> githubService.getUserSummary(username));
    }
}