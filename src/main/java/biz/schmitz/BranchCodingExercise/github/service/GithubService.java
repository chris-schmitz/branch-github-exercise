package biz.schmitz.BranchCodingExercise.github.service;

import biz.schmitz.BranchCodingExercise.github.api.GithubFeignClient;
import biz.schmitz.BranchCodingExercise.github.api.GithubRepoMetadata;
import biz.schmitz.BranchCodingExercise.github.api.GithubUser;
import biz.schmitz.BranchCodingExercise.github.domain.GithubUserSummary;
import biz.schmitz.BranchCodingExercise.github.exceptions.GithubUserNotFoundException;
import feign.FeignException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {
    private final GithubFeignClient githubFeignClient;

    GithubService(GithubFeignClient githubFeignClient) {
        this.githubFeignClient = githubFeignClient;
    }

    @Cacheable("GithubUserSummary")
    public GithubUserSummary getUserSummary(String username) {
        GithubUser user;
        List<GithubRepoMetadata> repos;
        try {
            user = githubFeignClient.getUserData(username);
            repos = githubFeignClient.getUserRepoMetadata(username);
        } catch (FeignException e) {
            if (e instanceof FeignException.NotFound) {
                throw new GithubUserNotFoundException(username);
            } else {
                throw e;
            }
        }
        return new GithubUserSummary(
                username,
                user.displayName(),
                user.avatar(),
                user.geoLocation(),
                user.email(),
                user.url(),
                user.createdAt(),
                repos
        );
    }
}
