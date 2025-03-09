package biz.schmitz.BranchCodingExercise.github.domain;

import biz.schmitz.BranchCodingExercise.github.api.GithubRepoMetadata;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GithubUserSummary(
        String userName,
        String displayName,
        String avatar,
        String geoLocation,
        String email,
        String url,
        String createdAt,
        List<GithubRepoMetadata> repos
) {
}

