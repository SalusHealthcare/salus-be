package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonNotFoundException;
import be.salushealthcare.salus.person.PersonRepository;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.person.UpdatePersonInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
