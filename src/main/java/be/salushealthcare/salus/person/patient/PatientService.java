package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.Person;
import be.salushealthcare.salus.person.PersonNotFoundException;
import be.salushealthcare.salus.person.PersonRepository;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.person.UpdatePersonInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;

    public List<Patient> getAll(int page, int size, PersonSort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        return repository.findAll(pageRequest).getContent();
    }

    @Transactional
    public Patient create(CreatePersonInput input) {
        return repository.saveAndFlush(Patient
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                        .medicalRecord(List.of())
                .build());
    }

    @Transactional
    public Patient update(Long personId, UpdatePersonInput input) {
        Patient person = getById(personId);
        person.setFirstName(input.getFirstName());
        person.setLastName(input.getLastName());
        person.setBirthDate(input.getBirthDate());
        person.setTelephoneNumber(input.getTelephoneNumber());
        person.setResidence(input.getResidence());
        person.setDomicile(input.getDomicile());
        return person;
    }

    public long countAll() {
        return repository.count();
    }

    public Patient getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }
}
