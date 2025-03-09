package biz.schmitz.BranchCodingExercise.github.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
        @JsonProperty("html_url")
        String url,
        String email,
        @JsonDeserialize(using = CustomDateDeserializer.class)
        String createdAt
) {
}
