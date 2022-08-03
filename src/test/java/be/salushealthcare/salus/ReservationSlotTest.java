package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.timeslot.TimeSlotInput;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotRepository;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlot;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotRepository;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationSlotTest {
    private static ReservationSlotService service;
    private static ReservationSlotRepository repository;

    @BeforeAll
    static void init() {
        repository = mock(ReservationSlotRepository.class);

        service = new ReservationSlotService(repository);
    }

    private Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    private Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

    private final Medic testMedic = Medic.builder()
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

    private final ReservationSlot testReservationSlot = ReservationSlot.builder()
            .medic(testMedic)
            .speciality(testMedic.getSpeciality())
            .startDateTime(LocalDateTime.parse("2021-12-12T00:00:00"))
            .durationInHours(8)
            .build();

    @Test
    void getReservationSlotById() {
        when(repository.getOne(anyLong())).thenReturn(testReservationSlot);

        ReservationSlot result = service.getReservationSlotById(0L);

        assertEquals(testReservationSlot, result);
    }

    @Test
    void getReservationSlotsTest() {
        List<ReservationSlot> expected = List.of(testReservationSlot);

        when(repository.findReservationSlotsByMedic_IdAndStartDateTimeBetween(anyLong(), any(), any())).thenReturn(expected);

        List<ReservationSlot> result = service.getReservationSlots("2021-12-12T00:00:00", "2021-12-12T00:00:00", 0L, null, null);

        assertEquals(expected, result);
    }

    @Test
    void addReservationSlotsTest() {
        TimeSlotInput input = TimeSlotInput.builder()
                .startDateTime(LocalDateTime.parse("2021-12-12T00:00:00"))
                .durationInHours(8)
                .build();

        service.addReservationSlots(testMedic, List.of(input));

        verify(repository, times(1)).saveAll(any());
    }
}
