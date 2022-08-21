package be.salushealthcare.salus.document;

import be.salushealthcare.salus.MedicalSpeciality;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findDocumentsByPatient_IdAndInsertedAtBetween(Pageable pageable, Long patientId,
                                                                    LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Document> findDocumentsByPatient_IdAndInsertedAtBetweenAndCategory(Pageable pageable, Long patientId,
                                                                               LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                                               MedicalSpeciality category);

    List<Document> findDocumentsByPatient_IdAndInsertedAtBetweenAndDocumentType(Pageable pageable, Long patientId,
                                                                                   LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                                                   DocumentType documentType);

    List<Document> findDocumentsByPatient_IdAndInsertedAtBetweenAndCategoryAndDocumentType(Pageable pageable, Long patientId,
                                                                                              LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                                                              MedicalSpeciality category, DocumentType documentType);
}