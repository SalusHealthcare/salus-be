package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.person.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Staff extends Person {
}
