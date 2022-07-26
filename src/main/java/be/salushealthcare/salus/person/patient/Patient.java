package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Patient extends Person {
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> medicalRecord;
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
