package be.salushealthcare.salus.timeslot.reservationslot;


import be.salushealthcare.salus.MedicalSpeciality;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSlotService {
    ReservationSlotRepository repository;

    public List<ReservationSlot> getReservationSlots(int page, int size, Long medicId, MedicalSpeciality speciality) {
        List<ReservationSlot> response = null;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "startDateTime"));
        if (medicId == null && speciality == null) response = repository.findReservationSlotsByBooked(pageRequest,false);
        if (medicId != null && speciality == null) response = repository.findReservationSlotsByMedic_IdAndBooked(pageRequest, medicId, false);
        if (medicId == null && speciality != null) response = repository.findReservationSlotsBySpecialityAndBooked(pageRequest, speciality, false);
        return response;
    }
}
