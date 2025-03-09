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

    @GetMapping("/{username}")
    public GithubUserSummary getUserSummary(@PathVariable("username") String username) {
        return githubService.getUserSummary(username);
    }
}
