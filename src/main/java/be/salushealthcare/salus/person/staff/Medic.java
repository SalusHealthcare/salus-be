package be.salushealthcare.salus.person.staff;

import be.salushealthcare.salus.MedicalSpeciality;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medic extends Staff {
    @Setter
    private MedicalSpeciality speciality;
}