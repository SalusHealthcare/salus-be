package be.salushealthcare.salus;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.team.Team;
import be.salushealthcare.salus.team.TeamService;
import be.salushealthcare.salus.team.TeamSort;
import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final PersonService personService;
    private final TeamService teamService;
    private final UserService userService;

    public List<Person> getAllPeople(int page, int size, PersonSort sort) {
        return personService.getAll(page, size, sort);
    }

    public long getPeopleCount() {
        return personService.countAll();
    }

    public Person getPerson(long personId) {
        return personService.getById(personId);
    }

    public List<Team> getAllTeams(int page, int size, TeamSort sort) {
        return teamService.getAll(page, size, sort);
    }

    public long getTeamCount() {
        return teamService.countAll();
    }

    public Team getTeam(long teamId) {
        return teamService.getById(teamId);
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
