package uk.co.gumtree.addressbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.gumtree.addressbook.domain.AddressBook;
import uk.co.gumtree.addressbook.domain.Contact;
import uk.co.gumtree.addressbook.domain.Gender;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressBookServiceTest {


    private AddressBookService addressBookService;

    @BeforeEach
    public void setup() {
        addressBookService = new AddressBookService();
    }

    @Test
    public void testNumberOfPersonWithGender() {

        AddressBook addressBook = AddressBook.builder()
                .contactList(Collections.singletonList(Contact.builder()
                        .dateOfBirth(LocalDate.MAX)
                        .lastName("Smith")
                        .firstName("Steve")
                        .gender(Gender.MALE)
                        .build()))
                .build() ;
        assertEquals(1 , addressBookService.numberOfPersonWithGender(addressBook, Gender.MALE));
        assertEquals(0 , addressBookService.numberOfPersonWithGender(addressBook, Gender.FEMALE));
    }

}
