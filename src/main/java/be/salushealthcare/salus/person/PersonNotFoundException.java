package be.salushealthcare.salus.person;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class PersonNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4624121128954970095L;
    private final String className;
    private final Long id;

    @Override
    public String getMessage() {
        return MessageFormat.format("{0} with ID ''{1}'' isn''t available", className, id);
    }
}
