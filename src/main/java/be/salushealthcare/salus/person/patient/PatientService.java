package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.document.DocumentInput;
import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.MedicService;
import be.salushealthcare.salus.reservation.Reservation;
import be.salushealthcare.salus.reservation.ReservationInput;
import be.salushealthcare.salus.timeslot.TimeSlotRepository;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserService userService;

    public List<Patient> getAll(int page, int size, PersonSort sort, String firstName, String lastName, String taxCode) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        List<Patient> result = null;
        if (firstName == null && lastName == null && taxCode == null) result = repository.findAll(pageRequest).getContent();
        if (firstName != null && lastName == null && taxCode == null) result = repository.findAllByFirstName(pageRequest, firstName);
        if (firstName == null && lastName != null && taxCode == null) result = repository.findAllByLastName(pageRequest, lastName);
        if (firstName != null && lastName != null && taxCode == null) result = repository.findAllByFirstNameAndLastName(pageRequest, firstName, lastName);
        if (firstName == null && lastName == null && taxCode != null) result = repository.findAllByTaxCode(pageRequest, taxCode);
        if (firstName != null && lastName == null && taxCode != null) result = repository.findAllByFirstNameAndTaxCode(pageRequest, firstName, taxCode);
        if (firstName == null && lastName != null && taxCode != null) result = repository.findAllByLastNameAndTaxCode(pageRequest, lastName, taxCode);
        if (firstName != null && lastName != null && taxCode != null) result = repository.findAllByFirstNameAndLastNameAndTaxCode(pageRequest, firstName, lastName, taxCode);
        return result;
    }

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

    public Patient uploadDocuments(Long patientId, List<DocumentInput> inputs) {
        Patient patient = repository.findById(patientId).orElseThrow();
        Medic editor = (Medic) userService.getCurrentUser().getPerson();
        List<Document> documents = inputs.stream()
                .map(d -> Document.builder()
                        .description(d.getDescription())
                        .documentType(d.getDocumentType())
                        .insertedAt(LocalDateTime.now())
                        .editor(editor)
                        .category(editor.getSpeciality())
                        .build())
                .collect(Collectors.toList());
        patient.getMedicalRecord().addAll(documents);
        repository.save(patient);
        return patient;
    }
}
