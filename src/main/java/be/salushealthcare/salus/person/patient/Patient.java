package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Patient extends Person {
}
