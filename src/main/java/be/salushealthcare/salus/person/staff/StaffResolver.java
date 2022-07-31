package be.salushealthcare.salus.person.staff;

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
public class StaffResolver implements GraphQLResolver<Staff> {
    private final UserService userService;
    private final ShiftSlotService shiftSlotService;

    public String getEmail(Staff person) {
        return userService.isAuthenticated() && person.getUser() != null ? person.getUser().getEmail() : null;
    }

    public Collection<String> getRoles(Staff person) {
        return person.getUser().getRoles();
    }

    public List<ShiftSlot> getShiftSlots (Staff staff, String startDate, String endDate) {
        return userService.isAdmin() || userService.getCurrentUser().getPersonId() == staff.getId() ?
                shiftSlotService.getShiftSlotsByStaffIdAndStartDateTimeIsBetween(staff.getId(), startDate, endDate) : null;
    }
}
