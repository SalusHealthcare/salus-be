package be.salushealthcare.salus.person.patient;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByFirstName(Pageable pageable, String firstName);
    List<Patient> findAllByLastName(Pageable pageable, String lastName);
    List<Patient> findAllByTaxCode(Pageable pageable, String taxCode);
    List<Patient> findAllByFirstNameAndLastName(Pageable pageable, String firstName, String lastName);
    List<Patient> findAllByFirstNameAndTaxCode(Pageable pageable, String firstName, String taxCode);
    List<Patient> findAllByLastNameAndTaxCode(Pageable pageable, String lastName, String taxCode);
    List<Patient> findAllByFirstNameAndLastNameAndTaxCode(Pageable pageable, String firstName, String lastName, String taxCode);
}
