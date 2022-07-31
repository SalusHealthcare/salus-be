package be.salushealthcare.salus.reservation;

import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        ReservationSlot reservationSlot = reservationSlotService.getReservationSlot(reservationInput.getReservationSlotId());
        reservationSlot.book();
        Reservation reservation = be.salushealthcare.salus.reservation.Reservation.builder()
                .description(reservationInput.getDescription())
                .bookedAt(LocalDateTime.now())
                .priority(reservationInput.getPriority())
                .patient(patient)
                .reservationSlot(reservationSlot)
                .build();
        repository.saveAndFlush(reservation);
        return reservation;
    }
}
