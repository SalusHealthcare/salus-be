package be.salushealthcare.salus.timeslot.reservationslot;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class AlreadyBookedException extends RuntimeException {
    private static final long serialVersionUID = 4624121121454970095L;
    private final Long id;

    @Override
    public String getMessage() {
        return MessageFormat.format("ReservationSlot with ID ''{0}'' was already booked", id);
    }
}
