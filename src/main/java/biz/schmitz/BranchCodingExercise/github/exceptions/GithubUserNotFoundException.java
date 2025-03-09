package biz.schmitz.BranchCodingExercise.github.exceptions;

public class GithubUserNotFoundException extends RuntimeException {
    public GithubUserNotFoundException(String username) {
        super("No user found with username: '" + username + "'");
    }
}
