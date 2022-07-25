package be.salushealthcare.salus.document;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.person.staff.Medic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private MedicalSpeciality category;

    private DocumentType documentType;

    @ManyToOne
    private Medic editor;

    /* TODO capire se si può aggiungere un campo del genere
    private File content
    */
}
