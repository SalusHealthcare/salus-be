package be.salushealthcare.salus.person.patient;

import be.salushealthcare.salus.person.CreatePersonInput;
import be.salushealthcare.salus.person.PersonNotFoundException;
import be.salushealthcare.salus.person.PersonSort;
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

    public Patient getPatientById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("Patient", id));
    }

    public List<Patient> getAll(int page, int size, PersonSort sort, String firstName, String lastName, String taxCode) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        List<Patient> result = null;
        if (firstName == null && lastName == null && taxCode == null) result = repository.findAll(pageRequest).getContent();
        if (firstName != null && lastName == null && taxCode == null) result = repository.findAllByFirstName(pageRequest, firstName);
        if (firstName == null && lastName != null && taxCode == null) result = repository.findAllByLastName(pageRequest, lastName);
        if (firstName != null && lastName != null && taxCode == null) result = repository.findAllByFirstNameAndLastName(pageRequest, firstName, lastName);
        if (firstName == null && lastName == null && taxCode != null) result = repository.findAllByTaxCode(pageRequest, taxCode);
        if (firstName != null && lastName == null && taxCode != null) result = repository.findAllByFirstNameAndTaxCode(pageRequest, firstName, taxCode);
        if (firstName == null && lastName != null && taxCode != null) result = repository.findAllByLastNameAndTaxCode(pageRequest, lastName, taxCode);
        if (firstName != null && lastName != null && taxCode != null) result = repository.findAllByFirstNameAndLastNameAndTaxCode(pageRequest, firstName, lastName, taxCode);
        return result;
    }

    @Transactional
    public Patient create(CreatePersonInput input) {
        return repository.saveAndFlush(Patient
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .taxCode(input.getTaxCode())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                .build());
    }
}
