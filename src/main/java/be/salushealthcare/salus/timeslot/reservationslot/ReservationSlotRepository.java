package be.salushealthcare.salus.timeslot.reservationslot;

import be.salushealthcare.salus.timeslot.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

    List<ReservationSlot> findReservationSlotsByMedic_Id(Long medicId);
    List<ReservationSlot> findReservationSlotsByMedic_IdAndAndBooked(Long medicId, boolean booked);

    List<ReservationSlot> findByBooked(boolean booked);
}
