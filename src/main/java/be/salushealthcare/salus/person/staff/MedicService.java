package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.person.PersonNotFoundException;
import be.salushealthcare.salus.person.PersonSort;
import be.salushealthcare.salus.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicService {
    private final MedicRepository repository;

    public Medic getMedicById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("Medic", id));
    }

    @Transactional
    public Medic create(CreateMedicInput input) {
        return repository.saveAndFlush(Medic
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .taxCode(input.getTaxCode())
                .birthDate(input.getBirthDate())
                .telephoneNumber(input.getTelephoneNumber())
                .residence(input.getResidence())
                .domicile(input.getDomicile())
                .speciality(input.getMedicalSpeciality())
                .deletable(true)
                .build());
    }

    public List<Medic> getMedics(int page, int size, PersonSort sort, MedicalSpeciality speciality) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        return speciality == null ?
                repository.findAll(pageRequest).getContent() :
                repository.findMedicsBySpeciality(pageRequest, speciality);
    }
}
