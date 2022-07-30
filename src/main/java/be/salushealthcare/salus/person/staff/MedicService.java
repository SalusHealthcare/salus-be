package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicService {
    private final MedicRepository repository;
    private final UserService userService;

    @Transactional
    public Medic create(CreateMedicInput input) {
        return repository.saveAndFlush(Medic
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                .speciality(input.getMedicalSpeciality())
                .shiftSlots(List.of())
                .reservationSlots(List.of())
                .build());
    }

    @Transactional
    public Medic addReservationSlots(List<TimeSlotInput> reservations) {
        Medic current = (Medic) userService.getCurrentUser().getPerson();
        List<ReservationSlot> reservationSlots = reservations.stream()
                .map(s -> ReservationSlot.builder()
                        .startDateTime(s.getStartDateTime())
                        .durationInHours(s.getDurationInHours())
                        .medic(current)
                        .speciality(current.getSpeciality())
                        .build())
                .collect(Collectors.toList());
        current.getReservationSlots().addAll(reservationSlots);
        return current;
    }

    public List<Medic> getMedics(int page, int size, PersonSort sort, MedicalSpeciality speciality) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        return speciality == null ?
                repository.findAll(pageRequest).getContent() :
                repository.findMedicsBySpeciality(pageRequest, speciality);
    }
}
