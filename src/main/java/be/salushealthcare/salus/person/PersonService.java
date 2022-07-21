package be.salushealthcare.salus.person;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    public List<Person> getAll(int page, int size, PersonSort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        return repository.findAll(pageRequest).getContent();
    }

    @Transactional
    public Person create(CreatePersonInput input) {
        return repository.saveAndFlush(Person
            .builder()
            .firstName(input.getFirstName())
            .lastName(input.getLastName())
            .birthDate(input.getBirthDate())
            .telephoneNumber(input.getTelephoneNumber())
            .title(input.getTitle())
            .teams(List.of())
            .build());
    }

    @Transactional
    public Person update(Long personId, UpdatePersonInput input) {
        Person person = getById(personId);
        person.setFirstName(input.getFirstName());
        person.setLastName(input.getLastName());
        person.setBirthDate(input.getBirthDate());
        person.setTelephoneNumber(input.getTelephoneNumber());
        return person;
    }

    public long countAll() {
        return repository.count();
    }

    public Person getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }
}
