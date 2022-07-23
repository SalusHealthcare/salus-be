package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.timeslot.ReservationSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "staff")
public class Medic extends Staff {
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservationSlot> reservationSlots;
}