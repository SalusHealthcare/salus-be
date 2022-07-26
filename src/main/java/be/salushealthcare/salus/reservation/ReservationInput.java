package be.salushealthcare.salus.reservation;

import lombok.Getter;

@Getter
public class ReservationInput {
    private Long reservationSlotId;
    private String description;
    private Priority priority;
}
