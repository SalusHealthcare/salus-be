package be.salushealthcare.salus.timeslot;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReservationSlot extends TimeSlot{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private boolean booked = false;

    @OneToOne(cascade = CascadeType.DETACH)
    private Reservation reservation;

    @OneToOne
    private Medic medic;

    private ReservationSlot book () {
        this.booked = true;
        return this;
    }
}
