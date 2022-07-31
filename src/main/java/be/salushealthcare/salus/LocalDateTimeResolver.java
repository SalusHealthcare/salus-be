package be.salushealthcare.salus;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeResolver implements GraphQLResolver<LocalDateTime> {
    public String getFormatString(LocalDateTime dateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(dateTime);
    }

    public String getIso(LocalDateTime dateTime) {
        return DateTimeFormatter.ISO_DATE_TIME.format(dateTime);
    }
}
