package be.salushealthcare.salus.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReservationInput {
    private Long reservationSlotId;
    private String description;
    private Priority priority;
}
