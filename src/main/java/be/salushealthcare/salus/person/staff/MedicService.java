package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicService {
    private final MedicRepository repository;

    @Transactional
    public Medic create(CreatePersonInput input) {
        return repository.saveAndFlush(Medic
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                .teams(List.of())
                .shiftSlots(List.of())
                .reservationSlots(List.of())
                .build());
    }
}
