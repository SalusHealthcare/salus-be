package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.staff.CreateMedicInput;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.MedicRepository;
import be.salushealthcare.salus.person.staff.MedicService;
import be.salushealthcare.salus.person.staff.Staff;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MedicTest {

    private static MedicService service;

    private static MedicRepository repository;

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

    @BeforeAll
    static void init() {
        repository = mock(MedicRepository.class);

        service = new MedicService(repository);
    }

    @Test
    void getMedicById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(testMedic));

        Staff result = service.getMedicById(0L);

        assertEquals(testMedic, result);
    }

    @Test
    void createTest() {
        CreateMedicInput input = CreateMedicInput.builder()
                .firstName("firstName")
                .lastName("lastName")
                .birthDate(LocalDate.parse("1999-09-09"))
                .telephoneNumber("+393333333333")
                .taxCode("MMMDDD99K09J092X")
                .residence(testResidence)
                .domicile(testDomicile)
                .medicalSpeciality(MedicalSpeciality.GENERAL_PRACTICE)
                .build();

        when(repository.saveAndFlush(any())).thenAnswer(i -> i.getArguments()[0]);

        Medic result = service.create(input);

        assertEquals(input.getFirstName(), result.getFirstName());
        assertEquals(input.getLastName(), result.getLastName());
        assertEquals(input.getTaxCode(), result.getTaxCode());
        assertEquals(input.getMedicalSpeciality(), result.getSpeciality());
    }

    @Test
    void getMedicsTest() {
        List<Medic> expected = List.of(testMedic);

        when(repository.findAll()).thenReturn(expected);

        List<Medic> result = service.getMedics(0, 100, null, null);

        assertEquals(expected, result);
    }
}
