package be.salushealthcare.salus;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.PersonSort;
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
    private final MedicService medicService;
    private final ReservationSlotService reservationSlotService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Person> getAllPeople(int page, int size, PersonSort sort) {
        return personService.getAll(page, size, sort);
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

    @PreAuthorize("hasAuthority('PATIENT')")
    public List<Medic> getMedics() {
        return medicService.getAll();
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    public List<ReservationSlot> getReservationSlots(Long medicId, Boolean booked) {
        return reservationSlotService.getReservationSlots(medicId, booked);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    public List<ReservationSlot> getAllReservationSlots(Boolean booked) {
        return reservationSlotService.getAllReservationSlots(booked);
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
