package be.salushealthcare.salus;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.MedicService;
import be.salushealthcare.salus.reservation.ReservationInput;
import be.salushealthcare.salus.team.Team;
import be.salushealthcare.salus.team.TeamService;
import be.salushealthcare.salus.team.TeamSort;
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
    private final TeamService teamService;
    private final UserService userService;
    private final PatientService patientService;
    private final MedicService medicService;
    private final ReservationSlotService reservationSlotService;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeams(int page, int size, TeamSort sort) {
        return teamService.getAll(page, size, sort);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public long getTeamCount() {
        return teamService.countAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Team getTeam(long teamId) {
        return teamService.getById(teamId);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<Medic> getMedics(int page, int size, PersonSort sort, MedicalSpeciality speciality, String team) {
        return medicService.getMedics(page, size, sort, speciality, team);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<ReservationSlot> getReservationSlots(int page, int size, Long medicId, MedicalSpeciality speciality) {
        if (medicId != null && speciality != null) {
            throw new RuntimeException("Cannot filter for both medicId and speciality");
        }
        return reservationSlotService.getReservationSlots(page, size, medicId, speciality);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    public List<Patient> getPatients(int page, int size, PersonSort sort, String firstName, String lastName, String taxCode) {
        return patientService.getAll(page, size, sort, firstName, lastName, taxCode);
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
