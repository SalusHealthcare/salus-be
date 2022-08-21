package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.person.staff.StaffRepository;
import be.salushealthcare.salus.person.staff.StaffService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StaffTest {

    private static StaffService service;

    private static StaffRepository repository;

    private Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    private Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

    @BeforeAll
    static void init() {
        repository = mock(StaffRepository.class);

        service = new StaffService(repository);
    }

    @Test
    void getStaffById() {
        Staff testStaff = Staff.builder()
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

        when(repository.findById(anyLong())).thenReturn(Optional.of(testStaff));

        Staff result = service.getStaffById(0L);

        assertEquals(testStaff, result);
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

        Staff result = service.create(input);

        assertEquals(input.getFirstName(), result.getFirstName());
        assertEquals(input.getLastName(), result.getLastName());
        assertEquals(input.getTaxCode(), result.getTaxCode());
    }
}
