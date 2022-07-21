package be.salushealthcare.salus.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Address {
    private Long id;

    private String street;

    private String number;

    private String city;

    private String province;

    @Column(name = "post_code")
    private String postCode;

    private String country;
}
