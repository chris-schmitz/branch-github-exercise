package biz.schmitz.BranchCodingExercise.github.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

@Headers({
        "User-Agent:cschmitz",
        "X-GitHub-Api-Version:2022-11-28",
        "Content-Type:application/json"
})
public interface GithubFeignClient {

    @RequestLine("GET /users/{username}")
    GithubUser getUserData(@Param String username);

    @RequestLine("GET /users/{username}/repos")
    List<GithubRepoMetadata> getUserRepoMetadata(@Param String username);
}
