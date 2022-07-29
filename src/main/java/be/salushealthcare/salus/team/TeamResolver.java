package be.salushealthcare.salus.team;

import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamResolver implements GraphQLResolver<Team> {
    private final TeamService teamService;
    private final UserService userService;

    public long getMemberCount(Team team) {
        return team.getMembers().size();
    }

    public boolean isLeader(Team team) {
        User currentUser = userService.getCurrentUser();
        return currentUser != null && team.isLeader(currentUser);
    }

    public boolean isMember(Team team) {
        User currentUser = userService.getCurrentUser();
        return teamService.isMember(team, currentUser);
    }
}
