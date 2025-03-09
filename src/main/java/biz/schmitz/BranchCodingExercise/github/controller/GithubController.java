package biz.schmitz.BranchCodingExercise.github.controller;

import biz.schmitz.BranchCodingExercise.github.domain.GithubUserSummary;
import biz.schmitz.BranchCodingExercise.github.service.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/github")
public class GithubController {

    private GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    /**
     * Get a github user's summary.
     * Given an valid github username, this endpoint will look up a subset of their user data
     * and a list of their repository metadata and return a summary. If the user can't be found
     * a 404 will be returned with a human-readable error message that could be displayed by the
     * front end.
     *
     * @param username - A valid github username
     * @return GithubUserSummary - A subset of github information for the user
     */
    @GetMapping("/{username}")
    public GithubUserSummary getUserSummary(@PathVariable("username") String username) {
        return githubService.getUserSummary(username);
    }
}
