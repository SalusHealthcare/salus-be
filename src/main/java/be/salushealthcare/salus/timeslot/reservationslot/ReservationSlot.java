package be.salushealthcare.salus.timeslot.reservationslot;

import be.salushealthcare.salus.MedicalSpeciality;
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

    private MedicalSpeciality speciality;

    public void book (Reservation reservation) {
        this.booked = true;
        this.reservation = reservation;
    }
}
