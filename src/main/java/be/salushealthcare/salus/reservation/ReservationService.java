package be.salushealthcare.salus.reservation;

import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// TODO fare i test per i casi negativi

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;
    private final ReservationSlotService reservationSlotService;

    public List<Reservation> getReservations(Long patientId, String startString, String endString) {
        LocalDateTime startDateTime = LocalDateTime.parse(startString);
        LocalDateTime endDateTime = LocalDateTime.parse(endString);
        return repository.findReservationsByPatient_IdAndReservationSlot_StartDateTimeIsBetween(patientId, startDateTime, endDateTime);
    }

    @Transactional
    public Reservation reserve(Patient patient, ReservationInput reservationInput) {
        ReservationSlot reservationSlot = reservationSlotService.getReservationSlotById(reservationInput.getReservationSlotId());
        if (reservationSlot.isBooked())
            throw new RuntimeException("The slot has already been booked");
        else {
            Reservation reservation = Reservation.builder()
                    .description(reservationInput.getDescription())
                    .bookedAt(LocalDateTime.now())
                    .priority(reservationInput.getPriority())
                    .patient(patient)
                    .reservationSlot(reservationSlot)
                    .build();
            reservationSlot.book(reservation);
            return repository.saveAndFlush(reservation);
        }
    }
}
