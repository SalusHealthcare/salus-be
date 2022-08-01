package be.salushealthcare.salus.document;

import be.salushealthcare.salus.MedicalSpeciality;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.Medic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository repository;
    private final PatientService patientService;

    public List<Document> getDocuments(Long patientId, int page, int size, String startString, String endString, MedicalSpeciality category, DocumentType documentType) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "insertedAt"));
        LocalDateTime startDateTime = LocalDateTime.parse(startString);
        LocalDateTime endDateTime = LocalDateTime.parse(endString);
        List<Document> response = null;

        if (category == null && documentType == null) response = repository.findDocumentsByPatient_IdAndAndInsertedAtBetween(pageRequest, patientId, startDateTime, endDateTime);
        if (category != null && documentType == null) response = repository.findDocumentsByPatient_IdAndAndInsertedAtBetweenAndCategory(pageRequest, patientId, startDateTime, endDateTime, category);
        if (category == null && documentType != null) response = repository.findDocumentsByPatient_IdAndAndInsertedAtBetweenAndDocumentType(pageRequest, patientId, startDateTime, endDateTime, documentType);
        if (category != null && documentType != null) response = repository.findDocumentsByPatient_IdAndAndInsertedAtBetweenAndCategoryAndDocumentType(pageRequest, patientId, startDateTime, endDateTime, category, documentType);

        return response;
    }

    public List<Document> insertDocuments(Medic editor, Long patientId, List<DocumentInput> inputs) {
        Patient patient = patientService.getPatient(patientId);
        List<Document> documents = inputs.stream()
                .map(d -> Document.builder()
                        .description(d.getDescription())
                        .documentType(d.getDocumentType())
                        .insertedAt(LocalDateTime.now())
                        .patient(patient)
                        .editor(editor)
                        .category(editor.getSpeciality())
                        .build())
                .collect(Collectors.toList());
        repository.saveAll(documents);
        return documents;
    }
}
