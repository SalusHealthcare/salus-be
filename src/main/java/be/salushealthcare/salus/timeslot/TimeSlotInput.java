package be.salushealthcare.salus.timeslot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TimeSlotInput {
    private LocalDateTime startDateTime;
    private Integer durationInHours;
}
