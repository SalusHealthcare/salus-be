package be.salushealthcare.salus.timeslot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class TimeSlot {
    // TODO convert later to LocalDateTime
    @Setter
    private LocalDate startDateTime;

    @Setter
    private Integer durationInHours;
}
