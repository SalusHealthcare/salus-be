package be.salushealthcare.salus;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.UpdatePersonInput;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.MedicService;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.person.staff.StaffService;
import be.salushealthcare.salus.security.BadCredentialsException;
import be.salushealthcare.salus.team.Team;
import be.salushealthcare.salus.team.TeamMemberService;
import be.salushealthcare.salus.team.TeamService;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotInput;
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
    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
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
    public User createMedicUser(CreateUserInput userInfo, CreatePersonInput personInput) {
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

    @PreAuthorize("isAuthenticated()")
    public Team createTeam(String name) {
        return teamService.create(name, userService.getCurrentUser());
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

    @PreAuthorize("isAuthenticated()")
    public Team joinTeam(long teamId) {
        teamMemberService.join(teamId, userService.getCurrentUser(), userService.isAdmin());
        return teamService.getById(teamId);
    }

    @PreAuthorize("isAuthenticated()")
    public Team leaveTeam(long teamId) {
        teamMemberService.leave(teamId, userService.getCurrentUser());
        return teamService.getById(teamId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteUser(long personId) {
        return userService.deleteUser(personId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteTeam(long teamId) {
        return teamService.deleteTeam(teamId);
    }

    @PreAuthorize("isAuthenticated()")
    public Team approveTeamMember(long teamId, long personId) {
        teamMemberService.approve(teamId, personId, userService.getCurrentUser());
        return teamService.getById(teamId);
    }

    @PreAuthorize("isAuthenticated()")
    public Team replaceTeamLeader(long teamId, long personId) {
        teamMemberService.replaceTeamLeader(teamId, personId, userService.getCurrentUser());
        return teamService.getById(teamId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Person promotePerson(long personId) {
        return userService.promotePerson(personId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Person unpromotePerson(long personId) {
        return userService.unpromotePerson(personId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Staff addShifts(long personId, List<ShiftSlotInput> shifts) {
        return staffService.addShifts(personId, shifts);
    }
}
