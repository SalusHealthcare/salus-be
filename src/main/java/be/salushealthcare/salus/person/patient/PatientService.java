package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.CreatePersonInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;

    @Transactional
    public Patient create(CreatePersonInput input) {
        return repository.saveAndFlush(Patient
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                        .medicalRecord(List.of())
                .build());
    }
}
