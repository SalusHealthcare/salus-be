package be.salushealthcare.salus.team;

import be.salushealthcare.salus.user.User;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamResolver implements GraphQLResolver<Team> {
    private final TeamMemberService teamMemberService;
    private final UserService userService;

    public List<TeamMember> getMembers(Team team, boolean withUnapproved) {
        User currentUser = userService.getCurrentUser();
        boolean allowedUnapproved = currentUser != null && (team.isLeader(currentUser) || userService.isAdmin());
        return teamMemberService.getAllByTeam(team.getId(), withUnapproved && allowedUnapproved);
    }

    public long getMemberCount(Team team, boolean withUnapproved) {
        User currentUser = userService.getCurrentUser();
        boolean allowedUnapproved = team.isLeader(currentUser) || userService.isAdmin();
        return teamMemberService.countAllByTeam(team, withUnapproved && allowedUnapproved);
    }

    public boolean isLeader(Team team) {
        User currentUser = userService.getCurrentUser();
        return currentUser != null && team.isLeader(currentUser);
    }

    public boolean isMember(Team team) {
        User currentUser = userService.getCurrentUser();
        return teamMemberService.isMember(team, currentUser);
    }
}
