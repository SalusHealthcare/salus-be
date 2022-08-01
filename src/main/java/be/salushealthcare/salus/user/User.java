package be.salushealthcare.salus.user;

import be.salushealthcare.salus.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @Column(name = "person_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long personId;

    @EqualsAndHashCode.Include
    private String email;

    @Setter
    @EqualsAndHashCode.Include
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "name")
    private Set<String> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    public User withRole(String role) {
        if (this.roles == null) {
            this.roles = Set.of(role);
        } else {
            this.roles.add(role);
        }
        return this;
    }

    public User withoutRole(String role) {
        if (this.roles != null) {
            this.roles.remove(role);
        }
        return this;
    }
}
