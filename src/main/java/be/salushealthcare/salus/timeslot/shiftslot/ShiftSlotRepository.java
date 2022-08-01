package be.salushealthcare.salus.timeslot.shiftslot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftSlotRepository extends JpaRepository<ShiftSlot, Long> {

    List<ShiftSlot> findShiftSlotsByStaff_IdAndStartDateTimeIsBetween(Long staffId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
