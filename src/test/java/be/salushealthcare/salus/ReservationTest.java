package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientRepository;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.reservation.Priority;
import be.salushealthcare.salus.reservation.Reservation;
import be.salushealthcare.salus.reservation.ReservationInput;
import be.salushealthcare.salus.reservation.ReservationRepository;
import be.salushealthcare.salus.reservation.ReservationService;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    private static ReservationService service;

    private static ReservationRepository repository;
    private static ReservationSlotService reservationSlotService;

    @BeforeAll
    static void init() {
        repository = mock(ReservationRepository.class);
        reservationSlotService = mock(ReservationSlotService.class);

        service = new ReservationService(repository, reservationSlotService);
    }

    private final Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    private final Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

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

    private final Patient testPatient = Patient.builder()
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

    ReservationSlot testReservationSlot = ReservationSlot.builder()
            .medic(testMedic)
            .speciality(testMedic.getSpeciality())
            .startDateTime(LocalDateTime.parse("2022-12-12T00:00:00"))
            .durationInHours(1)
            .build();

    Reservation testReservation = Reservation.builder()
            .reservationSlot(testReservationSlot)
            .bookedAt(LocalDateTime.parse("2022-12-12T00:00:00"))
            .priority(Priority.GREEN)
            .patient(testPatient)
            .description("appointment")
            .build();


    @Test
    void getReservationsTest() {
        List<Reservation> expected = List.of(testReservation);

        when(repository.findReservationsByPatient_IdAndReservationSlot_StartDateTimeIsBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(expected);

        List<Reservation> result = service.getReservations(0L, "2021-12-12T00:00:00", "2022-12-12T00:00:00");

        assertEquals(expected, result);
    }

    @Test
    void reserveTest() {
        ReservationInput input = ReservationInput.builder()
                .reservationSlotId(0L)
                .description("appointment")
                .priority(Priority.GREEN)
                .build();

        when(reservationSlotService.getReservationSlotById(anyLong())).thenReturn(testReservationSlot);
        when(repository.saveAndFlush(any())).thenAnswer(i -> i.getArguments()[0]);

        Reservation result = service.reserve(testPatient, input);

        assertEquals(testPatient, result.getPatient());
        assertEquals(testReservationSlot, result.getReservationSlot());
    }
}
