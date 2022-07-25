package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MedicResolver implements GraphQLResolver<Medic> {
    private final UserService userService;

    public String getEmail(Medic person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public Collection<String> getRoles(Medic person) {
        return person.getUser().getRoles();
    }
}
