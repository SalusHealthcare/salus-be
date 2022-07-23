package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
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
public class Medic extends Staff {
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservationSlot> reservationSlots;
}