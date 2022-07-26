package be.salushealthcare.salus.timeslot.reservationslot;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSlotService {
    ReservationSlotRepository repository;

    public List<ReservationSlot> getReservationSlots(Long medicId, Boolean booked) {
        return booked ? repository.findReservationSlotsByMedic_Id(medicId) : repository.findReservationSlotsByMedic_IdAndAndBooked(medicId, booked);
    }

    public List<ReservationSlot> getAllReservationSlots(Boolean booked) {
        return booked ? repository.findAll() : repository.findByBooked(booked);
    }
}
