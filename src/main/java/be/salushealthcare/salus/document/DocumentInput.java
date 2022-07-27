package be.salushealthcare.salus.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class DocumentInput {
    private final String description;
    private final DocumentType documentType;
}
