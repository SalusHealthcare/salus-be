package be.salushealthcare.salus.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TeamMemberId implements Serializable {
    private static final long serialVersionUID = 9203200408522164288L;
    @Column(name = "team_id")
    private Long teamId;
    @Column(name = "person_id")
    private Long personId;
}
