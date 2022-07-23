package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.team.TeamMember;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlot;
import lombok.AllArgsConstructor;
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
//@Table(name = "staff")
public class Staff extends Person {

    @OneToMany(cascade = CascadeType.ALL)
    private List<TeamMember> teams;

    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShiftSlot> shiftSlots;
}
