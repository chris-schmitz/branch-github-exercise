package biz.schmitz.BranchCodingExercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BranchCodingExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BranchCodingExerciseApplication.class, args);
    }

}
