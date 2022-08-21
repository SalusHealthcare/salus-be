package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlot;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotRepository;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShiftSlotTest {

    private static ShiftSlotService service;

    private static ShiftSlotRepository repository;

    @BeforeAll
    static void init() {
        repository = mock(ShiftSlotRepository.class);

        service = new ShiftSlotService(repository);
    }

    private Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    private Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

    private final Staff testStaff = Staff.builder()
            .id(0L)
            .firstName("firstName")
            .lastName("lastName")
            .birthDate(LocalDate.parse("1999-09-09"))
            .telephoneNumber("+393333333333")
            .taxCode("MMMDDD99K09J092X")
            .residence(testResidence)
            .domicile(testDomicile)
            .user(null)
            .build();

    private final ShiftSlot shiftSlot = ShiftSlot.builder()
            .staff(testStaff)
            .startDateTime(LocalDateTime.parse("2021-12-12T00:00:00"))
            .durationInHours(8)
            .build();

    @Test
    void getShiftSlotsByStaffIdAndStartDateTimeIsBetweenTest() {
        List<ShiftSlot> expected = List.of(shiftSlot);

        when(repository.findShiftSlotsByStaff_IdAndStartDateTimeIsBetween(anyLong(), any(), any())).thenReturn(expected);

        List<ShiftSlot> result = service.getShiftSlotsByStaffIdAndStartDateTimeIsBetween(0L, "2020-12-12T00:00:00", "2022-12-12T00:00:00");

        assertEquals(expected, result);
    }

    @Test
    void addShiftsTest() {
        TimeSlotInput input = TimeSlotInput.builder()
                .startDateTime(LocalDateTime.parse("2021-12-12T00:00:00"))
                .durationInHours(8)
                .build();

        service.addShifts(testStaff, List.of(input));

        verify(repository, times(1)).saveAll(any());
    }
}
