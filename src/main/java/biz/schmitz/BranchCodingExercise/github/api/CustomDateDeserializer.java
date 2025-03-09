package biz.schmitz.BranchCodingExercise.github.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<String> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Instant instant = Instant.parse(parser.getText());
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC")).format(FORMATTER);
    }
}
