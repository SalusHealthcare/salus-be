package be.salushealthcare.salus.person;

import be.salushealthcare.salus.address.Address;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreatePersonInput {
    private final String firstName;
    private final String lastName;
    private final String taxCode;
    private final LocalDate birthDate;
    private final String telephoneNumber;
    private final Address residence;
    private final Address domicile;
}
