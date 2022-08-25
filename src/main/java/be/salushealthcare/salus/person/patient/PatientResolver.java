package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.Roles;
import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.document.DocumentService;
import be.salushealthcare.salus.document.DocumentType;
import be.salushealthcare.salus.reservation.ReservationService;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PatientResolver implements GraphQLResolver<Patient> {
    private final UserService userService;
    private final DocumentService documentService;
    private final ReservationService reservationService;

    public String getEmail(Patient person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public Collection<String> getRoles(Patient person) {
        return person.getUser().getRoles();
    }

    public List<Document> getMedicalRecord(Patient patient, int page, int size, String startDate, String endDate, MedicalSpeciality category, DocumentType documentType) {
        return documentService.getDocuments(patient.getId(), page, size, startDate, endDate, category, documentType);
    }

    public List<be.salushealthcare.salus.reservation.Reservation> getReservations(Patient patient, String startDate, String endDate) {
        return userService.getCurrentUser().getPersonId() == patient.getId() ?
                reservationService.getReservations(patient.getId(), startDate, endDate) : null;
    }
}
