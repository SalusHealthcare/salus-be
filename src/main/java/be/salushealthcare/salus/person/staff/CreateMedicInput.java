package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class CreateMedicInput {
    private final String firstName;
    private final String lastName;
    private final String taxCode;
    private final LocalDate birthDate;
    private final String telephoneNumber;
    private final Address residence;
    private final Address domicile;
    private final MedicalSpeciality medicalSpeciality;
}
