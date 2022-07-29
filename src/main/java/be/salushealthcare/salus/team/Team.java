package be.salushealthcare.salus.team;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

// TODO general refactor to accomplish the purpose of the project
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Staff leader;
    @OneToMany
    private List<Staff> members;

    public Team assignLeader(Staff member) {
        this.leader = member;
        return this;
    }

    public boolean isLeader(User user) {
        return user != null && user.getPerson().equals(getLeader());
    }
}
