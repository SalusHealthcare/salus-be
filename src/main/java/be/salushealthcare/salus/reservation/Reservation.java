package be.salushealthcare.salus.reservation;

import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

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

    private LocalDateTime bookedAt;

    private Priority priority;

    @OneToOne
    private Patient patient;

    @OneToOne
    private ReservationSlot reservationSlot;
}
