package be.salushealthcare.salus.timeslot.reservationslot;


import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationSlotService {
    private final ReservationSlotRepository repository;

    public ReservationSlot getReservationSlotById(Long id) {
        return repository.getOne(id);
    }

    public List<ReservationSlot> getReservationSlots(String startString, String endString, Long medicId, MedicalSpeciality speciality, Boolean booked) {
        LocalDateTime startDateTime = LocalDateTime.parse(startString);
        LocalDateTime endDateTime = LocalDateTime.parse(endString);
        List<ReservationSlot> response = null;

        if (medicId == null && speciality == null) throw new RuntimeException("Must filter for medicId or speciality");
        if (medicId != null && speciality != null) throw new RuntimeException("Cannot filter for both medicId and speciality");

        if (medicId != null) response =
                booked == null ?
                        repository.findReservationSlotsByMedic_IdAndStartDateTimeBetween(medicId, startDateTime, endDateTime) :
                        repository.findReservationSlotsByMedic_IdAndStartDateTimeBetweenAndBooked(medicId, startDateTime, endDateTime, booked);

        if (speciality != null) response =
                booked == null ?
                        repository.findReservationSlotsBySpecialityAndStartDateTimeBetween(speciality, startDateTime, endDateTime) :
                        repository.findReservationSlotsBySpecialityAndStartDateTimeBetweenAndBooked(speciality, startDateTime, endDateTime, booked);

        return response;
    }

    @Transactional
    public Medic addReservationSlots(Medic medic, List<TimeSlotInput> reservations) {
        List<ReservationSlot> reservationSlots = reservations.stream()
                .map(s -> ReservationSlot.builder()
                        .startDateTime(s.getStartDateTime())
                        .durationInHours(s.getDurationInHours())
                        .medic(medic)
                        .speciality(medic.getSpeciality())
                        .build())
                .collect(Collectors.toList());
        repository.saveAll(reservationSlots);
        return medic;
    }
}
