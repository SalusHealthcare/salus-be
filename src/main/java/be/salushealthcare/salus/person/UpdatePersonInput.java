package be.salushealthcare.salus.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdatePersonInput {
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final String telephoneNumber;
    private final String title;
}
