package be.salushealthcare.salus.timeslot.shiftslot;

import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.person.staff.StaffRepository;
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
    ShiftSlotRepository repository;
    StaffRepository staffRepository;

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
}
