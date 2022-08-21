package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientRepository;
import be.salushealthcare.salus.person.patient.PatientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatientTest {

    private static PatientService service;

    private static PatientRepository repository;

    private Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    private Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

    private Patient testPatient = Patient.builder()
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
        repository = mock(PatientRepository.class);

        service = new PatientService(repository);
    }

    @Test
    void getStaffById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(testPatient));

        Patient result = service.getPatientById(0L);

        assertEquals(testPatient, result);
    }

    @Test
    void createTest() {
        CreatePersonInput input = CreatePersonInput.builder()
                .firstName("firstName")
                .lastName("lastName")
                .birthDate(LocalDate.parse("1999-09-09"))
                .telephoneNumber("+393333333333")
                .taxCode("MMMDDD99K09J092X")
                .residence(testResidence)
                .domicile(testDomicile)
                .build();

        when(repository.saveAndFlush(any())).thenAnswer(i -> i.getArguments()[0]);

        Patient result = service.create(input);

        assertEquals(input.getFirstName(), result.getFirstName());
        assertEquals(input.getLastName(), result.getLastName());
        assertEquals(input.getTaxCode(), result.getTaxCode());
    }

    @Test
    void getAllTest() {
        List<Patient> expected = List.of(testPatient);

        Page<Patient> expectedPage = new PageImpl<>(expected);

        when(repository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        List<Patient> result = service.getAll(0, 100, null, null, null, null);

        assertEquals(expected, result);
    }
}
