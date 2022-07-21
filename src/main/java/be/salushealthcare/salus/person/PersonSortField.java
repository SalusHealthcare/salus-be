package be.salushealthcare.salus.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PersonSortField {
    LAST_NAME("lastName"), BIRTH_DATE("birthDate");

    private final String queryField;
}
