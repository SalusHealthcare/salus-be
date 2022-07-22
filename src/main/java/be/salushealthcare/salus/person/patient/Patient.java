package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.person.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
// TODO capire se è meglio cambiare la strategy perché forse così non posso avere due account uno per staff e uno per il paziente nel caso la stessa persona abbia bisogno di entrambi
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
//@Table(name = "patient")
public class Patient extends Person {
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> medicalRecord;
}
