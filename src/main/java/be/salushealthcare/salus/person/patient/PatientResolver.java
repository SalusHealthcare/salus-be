package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PatientResolver implements GraphQLResolver<Patient> {
    private final UserService userService;

    public String getEmail(Patient person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public Collection<String> getRoles(Patient person) {
        return person.getUser().getRoles();
    }
}