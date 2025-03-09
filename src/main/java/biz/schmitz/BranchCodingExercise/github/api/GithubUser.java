package biz.schmitz.BranchCodingExercise.github.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUser(
        String userName,
        @JsonProperty("name")
        String displayName,
        @JsonProperty("avatar_url")
        String avatar,
        @JsonProperty("location")
        String geoLocation,
        String url,
        String email,
        @JsonProperty("created_at")
        String createdAt
) {
}
