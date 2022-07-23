package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PatientResolver implements GraphQLResolver<Patient> {
    private final UserService userService;

    public String getEmail(Patient patient) {
        return userService.isAuthenticated() && patient.getUser() != null ? patient.getUser().getEmail() : null;
    }

    public String getTelephoneNumber(Patient patient) {
        return userService.isAuthenticated() ? patient.getTelephoneNumber() : null;
    }

    public Collection<String> getRoles(Patient patient) {
        return patient.getUser().getRoles();
    }
}