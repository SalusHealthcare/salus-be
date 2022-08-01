package be.salushealthcare.salus.person;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "taxCode")
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @Setter
    private LocalDate birthDate;

    @Setter
    private String telephoneNumber;

    @Setter
    private String taxCode;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Address domicile;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Address residence;

    @OneToOne(mappedBy = "person")
    private User user;
}
