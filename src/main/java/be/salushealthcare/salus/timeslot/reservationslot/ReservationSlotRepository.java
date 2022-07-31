package be.salushealthcare.salus.timeslot.reservationslot;

import be.salushealthcare.salus.MedicalSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

    List<ReservationSlot> findReservationSlotsByMedic_IdAndStartDateTimeBetween(Long medicId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<ReservationSlot> findReservationSlotsByMedic_IdAndStartDateTimeBetweenAndBooked(Long medicId, LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean booked);

    List<ReservationSlot> findReservationSlotsBySpecialityAndStartDateTimeBetween(MedicalSpeciality speciality, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<ReservationSlot> findReservationSlotsBySpecialityAndStartDateTimeBetweenAndBooked(MedicalSpeciality speciality, LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean booked);

    List<ReservationSlot> findReservationSlotsByBooked(Boolean booked);
}
