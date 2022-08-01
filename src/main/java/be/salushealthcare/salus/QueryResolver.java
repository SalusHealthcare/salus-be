package be.salushealthcare.salus;

import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.document.DocumentService;
import be.salushealthcare.salus.document.DocumentType;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.MedicService;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final PersonService personService;
    private final UserService userService;
    private final PatientService patientService;
    private final MedicService medicService;
    private final ReservationSlotService reservationSlotService;
    private final DocumentService documentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Person> getAllPeople(int page, int size, PersonSort sort, String role) {
        return personService.getAll(page, size, sort, role);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public long getPeopleCount() {
        return personService.countAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Person getPerson(long personId) {
        return personService.getById(personId);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<Medic> getMedics(int page, int size, PersonSort sort, MedicalSpeciality speciality) {
        return medicService.getMedics(page, size, sort, speciality);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<ReservationSlot> getAvailableReservationSlots(String startDate, String endDate, Long medicId, MedicalSpeciality speciality) {
        return reservationSlotService.getReservationSlots(startDate, endDate, medicId, speciality, false);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    public List<Patient> getPatients(int page, int size, PersonSort sort, String firstName, String lastName, String taxCode) {
        return patientService.getAll(page, size, sort, firstName, lastName, taxCode);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    public List<Document> getDocuments(Long patientId, int page, int size, String startDate, String endDate, MedicalSpeciality category, DocumentType documentType) {
        return documentService.getDocuments(patientId, page, size, startDate, endDate, category, documentType);
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PreAuthorize("hasAuthority('USER')")
    public Patient getCurrentPatient() {
        return (Patient) userService.getCurrentUser().getPerson();
    }

    @PreAuthorize("hasAuthority('STAFF')")
    public Staff getCurrentStaff() {
        return (Staff) userService.getCurrentUser().getPerson();
    }

    @PreAuthorize("hasAuthority('MEDIC')")
    public Medic getCurrentMedic() {
        return (Medic) userService.getCurrentUser().getPerson();
    }
}
