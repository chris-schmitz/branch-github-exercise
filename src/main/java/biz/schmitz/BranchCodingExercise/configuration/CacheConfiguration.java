package biz.schmitz.BranchCodingExercise.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        var cache = Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES);
        var cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(cache);

        return cacheManager;
    }
}
