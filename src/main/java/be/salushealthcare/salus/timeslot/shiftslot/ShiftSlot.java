package be.salushealthcare.salus.timeslot.shiftslot;

import be.salushealthcare.salus.timeslot.TimeSlot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShiftSlot extends TimeSlot {
}
