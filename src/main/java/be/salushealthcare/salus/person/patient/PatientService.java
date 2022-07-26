package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.reservation.Reservation;
import be.salushealthcare.salus.reservation.ReservationInput;
import be.salushealthcare.salus.timeslot.TimeSlotRepository;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserService userService;

    @Transactional
    public Patient create(CreatePersonInput input) {
        return repository.saveAndFlush(Patient
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                .medicalRecord(List.of())
                .reservations(List.of())
                .build());
    }

    @Transactional
    public Patient reserve(ReservationInput reservationInput) {
        Patient current = (Patient) userService.getCurrentUser().getPerson();
        Reservation reservation = Reservation.builder()
                .description(reservationInput.getDescription())
                .bookedAt(LocalDateTime.now())
                .priority(reservationInput.getPriority())
                .patient(current)
                .reservationSlot((ReservationSlot) timeSlotRepository.getOne(reservationInput.getReservationSlotId()))
                .build();
        current.getReservations().add(reservation);
        return current;
    }
}
