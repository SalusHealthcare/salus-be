package be.salushealthcare.salus;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateResolver implements GraphQLResolver<LocalDate> {
    public String getFormatString(LocalDate date, String format) {
        return DateTimeFormatter.ofPattern(format).format(date);
    }

    public String getIso(LocalDate date) {
        return DateTimeFormatter.ISO_DATE.format(date);
    }
}
