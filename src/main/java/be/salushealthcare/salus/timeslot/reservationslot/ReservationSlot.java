package be.salushealthcare.salus.timeslot.reservationslot;

import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.reservation.Reservation;
import be.salushealthcare.salus.timeslot.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReservationSlot extends TimeSlot {
    @Builder.Default
    private boolean booked = false;

    @OneToOne(cascade = CascadeType.DETACH)
    private Reservation reservation;

    @OneToOne
    private Medic medic;

    // TODO capire se necessario cambiare questo metodo in futuro
    private ReservationSlot book () {
        this.booked = true;
        return this;
    }
}
