package be.salushealthcare.salus.document;

import be.salushealthcare.salus.MedicalSpeciality;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findDocumentsByPatient_IdAndAndInsertedAtBetween(Pageable pageable, Long patientId,
                                                                    LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Document> findDocumentsByPatient_IdAndAndInsertedAtBetweenAndCategory(Pageable pageable, Long patientId,
                                                                               LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                                               MedicalSpeciality category);

    List<Document> findDocumentsByPatient_IdAndAndInsertedAtBetweenAndDocumentType(Pageable pageable, Long patientId,
                                                                                   LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                                                   DocumentType documentType);

    List<Document> findDocumentsByPatient_IdAndAndInsertedAtBetweenAndCategoryAndDocumentType(Pageable pageable, Long patientId,
                                                                                              LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                                                              MedicalSpeciality category, DocumentType documentType);
}