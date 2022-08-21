package be.salushealthcare.salus;

import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.document.DocumentInput;
import be.salushealthcare.salus.document.DocumentService;
import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.UpdatePersonInput;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.CreateMedicInput;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.MedicService;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.person.staff.StaffService;
import be.salushealthcare.salus.reservation.Reservation;
import be.salushealthcare.salus.reservation.ReservationInput;
import be.salushealthcare.salus.reservation.ReservationService;
import be.salushealthcare.salus.security.BadCredentialsException;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotService;
import be.salushealthcare.salus.user.CreateUserInput;
import be.salushealthcare.salus.user.UpdatePasswordInput;
import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {
    private final UserService userService;
    private final PersonService personService;
    private final PatientService patientService;
    private final StaffService staffService;
    private final MedicService medicService;
    private final ShiftSlotService shiftSlotService;
    private final DocumentService documentService;
    private final ReservationSlotService reservationSlotService;
    private final ReservationService reservationService;
    private final AuthenticationProvider authenticationProvider;

    public User createPatientUser(CreateUserInput userInfo, CreatePersonInput personInput) {
        Patient person = patientService.create(personInput);
        return userService.createUser(person, userInfo);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User createStaffUser(CreateUserInput userInfo, CreatePersonInput personInput) {
        Staff person = staffService.create(personInput);
        return userService.createUser(person, userInfo);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User createMedicUser(CreateUserInput userInfo, CreateMedicInput personInput) {
        Medic person = medicService.create(personInput);
        return userService.createUser(person, userInfo);
    }

    @PreAuthorize("isAuthenticated()")
    public User updatePassword(UpdatePasswordInput input) {
        return userService.updatePassword(userService.getCurrentUser().getPersonId(), input);
    }

    @PreAuthorize("isAuthenticated()")
    public Person updatePerson(UpdatePersonInput input) {
        return personService.update(userService.getCurrentUser().getPersonId(), input);
    }

    @PreAuthorize("isAnonymous()")
    public User login(String email, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return userService.getCurrentUser();
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteUser(long personId) {
        return userService.deleteUser(personId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff promoteStaff(long personId) {
        return userService.promoteStaff(personId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff unpromoteStaff(long personId) {
        return userService.unpromoteStaff(personId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff addShifts(long staffId, List<TimeSlotInput> shifts) {
        return shiftSlotService.addShifts((Staff) personService.getById(staffId), shifts);
    }

    @PreAuthorize("hasAuthority('MEDIC')")
    public Medic addReservationSlot(List<TimeSlotInput> reservationSlots) {
        return reservationSlotService.addReservationSlots((Medic) userService.getCurrentUser().getPerson(), reservationSlots);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    public Reservation reserve(ReservationInput reservation) {
        return reservationService.reserve((Patient) userService.getCurrentUser().getPerson(), reservation);
    }

    @PreAuthorize("hasAuthority('MEDIC')")
    public List<Document> insertDocuments(Long patientId, List<DocumentInput> documents) {
        return documentService.insertDocuments((Medic) userService.getCurrentUser().getPerson(), patientId, documents);
    }
}
