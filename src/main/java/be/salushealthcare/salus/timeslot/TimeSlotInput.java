package be.salushealthcare.salus.timeslot;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeSlotInput {
    private LocalDateTime startDateTime;
    private Integer durationInHours;
}
