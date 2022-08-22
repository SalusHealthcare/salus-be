package be.salushealthcare.salus.timeslot.shiftslot;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonNotFoundException;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftSlotService {
    private final ShiftSlotRepository repository;

    public List<ShiftSlot> getShiftSlotsByStaffIdAndStartDateTimeIsBetween(Long staffId, String startString, String endString) {
        LocalDateTime startDateTime = LocalDateTime.parse(startString);
        LocalDateTime endDateTime = LocalDateTime.parse(endString);
        return repository.findShiftSlotsByStaff_IdAndStartDateTimeIsBetween(staffId, startDateTime, endDateTime);
    }

    @Transactional
    public Staff addShifts(Staff staff, List<TimeSlotInput> shifts) {
        List<ShiftSlot> shiftSlots = shifts.stream()
                .map(s -> ShiftSlot.builder()
                        .staff(staff)
                        .startDateTime(s.getStartDateTime())
                        .durationInHours(s.getDurationInHours())
                        .build())
                .collect(Collectors.toList());
        repository.saveAll(shiftSlots);
        return staff;
    }

    @Transactional
    public ShiftSlot updateShift(long shiftId, TimeSlotInput input) {
        ShiftSlot shiftSlot = getById(shiftId);
        shiftSlot.setStartDateTime(input.getStartDateTime());
        shiftSlot.setDurationInHours(input.getDurationInHours());
        repository.save(shiftSlot);
        return shiftSlot;
    }

    @Transactional
    public boolean deleteShift(long shiftId) {
        // if it doesn't find the shiftSlot the software would throw an exception and not return true
        ShiftSlot shiftSlot = getById(shiftId);
        repository.delete(shiftSlot);
        return true;
    }

    public ShiftSlot getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("ShiftSlot", id));
    }
}
