package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.person.CreatePersonInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository repository;

    @Transactional
    public Staff create(CreatePersonInput input) {
        return repository.saveAndFlush(Staff
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                .teams(List.of())
                .shiftSlots(List.of())
                .build());
    }
}
