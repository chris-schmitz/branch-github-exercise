package biz.schmitz.BranchCodingExercise.configuration;

import biz.schmitz.BranchCodingExercise.github.api.GithubFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    private ObjectMapper objectMapper;

    FeignClientConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public GithubFeignClient buildFeignClient(@Value("${github.api.url}") String apiUrl) {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(GithubFeignClient.class, apiUrl);
    }
}
