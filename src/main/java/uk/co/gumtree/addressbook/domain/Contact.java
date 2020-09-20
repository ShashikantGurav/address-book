package uk.co.gumtree.addressbook.domain;

import lombok.Builder;
import lombok.Data;
import org.joda.time.LocalDate;


@Data
@Builder
public class Contact {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
}
