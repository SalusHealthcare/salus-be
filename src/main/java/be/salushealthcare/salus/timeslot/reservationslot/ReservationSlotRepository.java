package be.salushealthcare.salus.timeslot.reservationslot;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.timeslot.TimeSlot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// TODO understand if I should filter the results to not shoe reservation that refer to a date before now
public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

    List<ReservationSlot> findReservationSlotsByMedic_IdAndBooked(Pageable pageable, Long medicId, Boolean booked);
    List<ReservationSlot> findReservationSlotsBySpecialityAndBooked(Pageable pageable, MedicalSpeciality speciality, Boolean booked);

    List<ReservationSlot> findReservationSlotsByBooked(Pageable pageable, Boolean booked);
}
