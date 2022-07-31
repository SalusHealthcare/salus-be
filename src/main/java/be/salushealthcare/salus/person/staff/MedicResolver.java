package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlot;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotService;
import be.salushealthcare.salus.user.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MedicResolver implements GraphQLResolver<Medic> {
    private final UserService userService;
    private final ShiftSlotService shiftSlotService;
    private final ReservationSlotService reservationSlotService;

    public String getEmail(Medic person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public Collection<String> getRoles(Medic person) {
        return person.getUser().getRoles();
    }

    public List<ShiftSlot> getShiftSlots (Medic medic, String startDate, String endDate) {
        return userService.isAdmin() || userService.getCurrentUser().getPersonId() == medic.getId() ?
                shiftSlotService.getShiftSlotsByStaffIdAndStartDateTimeIsBetween(medic.getId(), startDate, endDate) : null;
    }

    public List<ReservationSlot> getReservationSlots (Medic medic, String startDate, String endDate, Boolean booked) {
        return userService.isAdmin() || userService.getCurrentUser().getPersonId() == medic.getId() ?
                reservationSlotService.getReservationSlots(startDate, endDate, medic.getId(), null, booked) : null;
    }
}
