package be.salushealthcare.salus;

import be.salushealthcare.salus.document.Document;
import be.salushealthcare.salus.document.DocumentInput;
import be.salushealthcare.salus.document.DocumentRepository;
import be.salushealthcare.salus.document.DocumentService;
import be.salushealthcare.salus.document.DocumentType;
import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.patient.PatientService;
import be.salushealthcare.salus.person.staff.Medic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO extent tests to all cases

public class DocumentTest {

    private static DocumentService service;

    private static DocumentRepository repository;
    private static PatientService patientService;

    private Patient patientTest = Patient.builder()
            .id(0L)
            .firstName("firstName")
            .lastName("lastName")
            .birthDate(LocalDate.parse("1999-09-09"))
            .telephoneNumber("+393333333333")
            .taxCode("MMMDDD99K09J092X")
            .build();

    private Medic medicTest = Medic.builder()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .birthDate(LocalDate.parse("1999-09-19"))
            .telephoneNumber("+393333333331")
            .taxCode("MMMDDD99K09J092Y")
            .speciality(MedicalSpeciality.GENERAL_PRACTICE)
            .build();

    @BeforeAll
    static void init() {
        repository = mock(DocumentRepository.class);
        patientService = mock(PatientService.class);

        service = new DocumentService(repository, patientService);
    }

    @Test
    void getDocumentsTest() {
        List<Document> expected = List.of(
                Document.builder()
                        .id(0L)
                        .description("description")
                        .insertedAt(LocalDateTime.parse("2022-08-03T00:00:00"))
                        .category(MedicalSpeciality.GENERAL_PRACTICE)
                        .documentType(DocumentType.REPORT)
                        .patient(patientTest)
                        .editor(medicTest)
                        .build()
        );

        when(repository.findDocumentsByPatient_IdAndInsertedAtBetween(any(),anyLong(), any(), any())).thenReturn(expected);

        List<Document> documentList = service.getDocuments(0L, 0, 100, "2000-01-01T00:00:00", "2022-12-12T00:00:00", null, null);

        assertEquals(expected, documentList);
    }

    @Test
    void insertDocumentsTest() {
        DocumentInput input = new DocumentInput("description", DocumentType.PRESCRIPTION);

        when(patientService.getPatientById(anyLong())).thenReturn(patientTest);

        List<Document> documentList = service.insertDocuments(medicTest, 0L, List.of(input));

        assertEquals(patientTest, documentList.get(0).getPatient());
        assertEquals(medicTest, documentList.get(0).getEditor());
        verify(repository, times(1)).saveAll(any());
    }
}
