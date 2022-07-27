package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.MedicalSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicRepository extends JpaRepository<Medic, Long> {
    List<Medic> findMedicsBySpeciality(MedicalSpeciality speciality);
}
