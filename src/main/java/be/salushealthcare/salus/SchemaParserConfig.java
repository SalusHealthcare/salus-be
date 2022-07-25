package be.salushealthcare.salus;

import be.salushealthcare.salus.person.patient.Patient;
import be.salushealthcare.salus.person.staff.Medic;
import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.timeslot.reservationslot.ReservationSlot;
import be.salushealthcare.salus.timeslot.shiftslot.ShiftSlot;
import graphql.kickstart.tools.SchemaParserDictionary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaParserConfig {

    @Bean
    public SchemaParserDictionary schemaParserDictionary() {
        return new SchemaParserDictionary()
                .add(Patient.class)
                .add(Staff.class)
                .add(Medic.class)
                .add(ShiftSlot.class)
                .add(ReservationSlot.class);
    }

}