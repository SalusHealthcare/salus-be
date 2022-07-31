package be.salushealthcare.salus.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByPatient_IdAndReservationSlot_StartDateTimeIsBetween(Long patientId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
