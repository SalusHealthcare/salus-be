package be.salushealthcare.salus.reservation;

import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.timeslot.ReservationSlot;
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
@EqualsAndHashCode
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    // TODO convert it to LocalDateTime
    private String bookedAt;
    private Priority priority;

    @OneToOne
    private Patient patient;

    @OneToOne
    private ReservationSlot reservationSlot;
}
