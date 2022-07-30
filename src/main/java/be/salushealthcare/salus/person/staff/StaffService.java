package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .shiftSlots(List.of())
                .build());
    }

    @Transactional
    public Staff addShifts(long personId, List<TimeSlotInput> shifts) {
        Staff staff = repository.getOne(personId);
        List<ShiftSlot> shiftSlots = shifts.stream()
                .map(s -> ShiftSlot.builder()
                        .startDateTime(s.getStartDateTime())
                        .durationInHours(s.getDurationInHours())
                        .build())
                .collect(Collectors.toList());
        staff.getShiftSlots().addAll(shiftSlots);
        return staff;
    }
}
