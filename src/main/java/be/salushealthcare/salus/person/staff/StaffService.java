package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.PersonNotFoundException;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository repository;

    public Staff getStaffById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("Staff", id));
    }
    @Transactional
    public Staff create(CreatePersonInput input) {
        return repository.saveAndFlush(Staff
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .build());
    }
}
