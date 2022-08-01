package be.salushealthcare.salus.person;

import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PersonResolver implements GraphQLResolver<Person> {
    private final UserService userService;

    public String getEmail(Person person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public String getTelephoneNumber(Person person) {
        return userService.isAuthenticated() ? person.getTelephoneNumber() : null;
    }

    public Collection<String> getRoles(Person person) {
        return person.getUser().getRoles();
    }
}
