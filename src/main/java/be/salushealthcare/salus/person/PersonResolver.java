package be.salushealthcare.salus.person;

import be.salushealthcare.salus.team.TeamMember;
import be.salushealthcare.salus.team.TeamMemberService;
import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonResolver implements GraphQLResolver<Person> {
    private final UserService userService;
    private final TeamMemberService teamMemberService;

    public String getEmail(Person person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public String getTelephoneNumber(Person person) {
        return userService.isAuthenticated() ? person.getTelephoneNumber() : null;
    }

    public Collection<String> getRoles(Person person) {
        return person.getUser().getRoles();
    }

    public List<TeamMember> getTeams(Person person, boolean withUnapproved) {
        User currentUser = userService.getCurrentUser();
        boolean allowedUnapproved = currentUser.getPerson().equals(person) || userService.isAdmin();
        return teamMemberService.getAllByPerson(person.getId(), withUnapproved && allowedUnapproved);
    }

    public long getTeamCount(Person person) {
        return teamMemberService.countAllByPerson(person.getId());
    }
}
