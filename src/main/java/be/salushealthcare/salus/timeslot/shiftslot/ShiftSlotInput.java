package be.salushealthcare.salus.timeslot.shiftslot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShiftSlotInput {
    private LocalDateTime startDateTime;
    private Integer durationInHours;
}
