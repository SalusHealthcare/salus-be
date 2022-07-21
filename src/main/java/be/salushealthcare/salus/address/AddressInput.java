package be.salushealthcare.salus.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressInput {
    private final String street;
    private final String number;
    private final String city;
    private final String province;
    private final String postCode;
    private final String country;
}
