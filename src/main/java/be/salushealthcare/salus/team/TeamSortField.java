package be.salushealthcare.salus.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamSortField {
    NAME("name");

    private final String queryField;;
}
