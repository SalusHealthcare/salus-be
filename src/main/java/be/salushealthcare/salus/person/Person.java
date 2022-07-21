package be.salushealthcare.salus.person;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.team.TeamMember;
import be.salushealthcare.salus.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "taxCode")
public class Person {
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

    // TODO propagate taxCode change to all the project
    @Setter
    private String taxCode;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Address domicile;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Address residence;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<TeamMember> teams;

    @OneToOne(mappedBy = "person")
    private User user;
}
