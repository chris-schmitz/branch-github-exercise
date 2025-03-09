package biz.schmitz.BranchCodingExercise.github.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
        String createdAt
) {
}
