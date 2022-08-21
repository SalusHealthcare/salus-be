package be.salushealthcare.salus;

import be.salushealthcare.salus.address.Address;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonRepository;
import be.salushealthcare.salus.person.PersonService;
import be.salushealthcare.salus.person.UpdatePersonInput;
import be.salushealthcare.salus.person.staff.Staff;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonTest {
    private static PersonService service;

    private static PersonRepository repository;

    private Address testResidence = new Address(0L, "via Roma", "1", "Roma", "RM", "00159", "Italy");
    private Address testDomicile = new Address(1L, "via Roma", "1", "Roma", "RM", "00159", "Italy");

    private Person testPerson = Staff.builder()
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
        repository = mock(PersonRepository.class);
        service = new PersonService(repository);
    }

    @Test
    void getAllTest() {
        List<Person> expected = List.of(testPerson);

        Page<Person> expectedPage = new PageImpl<>(expected);

        when(repository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        List<Person> result = service.getAll(0, 100, null, null);

        assertEquals(expected, result);
    }

    @Test
    void updateTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(testPerson));

        UpdatePersonInput updatePersonInput = UpdatePersonInput.builder()
                .firstName("Adelmo")
                .telephoneNumber("+324444444444")
                .build();

        Person result = service.update(0L, updatePersonInput);

        assertEquals("Adelmo", result.getFirstName());
        assertEquals("+324444444444", result.getTelephoneNumber());
        assertEquals("lastName", result.getLastName());
    }

    @Test
    void countAllTest() {
        when(repository.count()).thenReturn(1L);

        Long result = service.countAll();

        assertEquals(1L, result);
        verify(repository, times(1)).count();
    }

    @Test
    void getByIdTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(testPerson));

        Person result = service.getById(0L);

        assertEquals(testPerson, result);
        verify(repository, times(1)).findById(anyLong());
    }
}
