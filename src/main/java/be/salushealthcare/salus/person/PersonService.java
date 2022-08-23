package be.salushealthcare.salus.person;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    public List<Person> getAll(int page, int size, PersonSort sort, String role) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        List<Person> response;
        if (StringUtils.isEmpty(role)) {
            response = repository.findAll(pageRequest).getContent();
        } else {
            response = repository.findAllByUserRoles(pageRequest, role);
        }
        return response;
    }

    @Transactional
    public Person update(Long personId, UpdatePersonInput input) {
        Person person = getById(personId);
        if (input.getFirstName() != null) person.setFirstName(input.getFirstName());
        if (input.getLastName() != null) person.setLastName(input.getLastName());
        if (input.getTelephoneNumber() != null) person.setTelephoneNumber(input.getTelephoneNumber());
        if (input.getResidence() != null) person.setResidence(input.getResidence());
        if (input.getDomicile() != null) person.setDomicile(input.getDomicile());
        return person;
    }

    public long countAll() {
        return repository.count();
    }

    public Person getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person", id));
    }
}
