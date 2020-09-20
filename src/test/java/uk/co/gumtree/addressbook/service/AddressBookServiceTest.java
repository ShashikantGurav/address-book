package uk.co.gumtree.addressbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.gumtree.addressbook.domain.AddressBook;
import uk.co.gumtree.addressbook.domain.Contact;
import uk.co.gumtree.addressbook.domain.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressBookServiceTest {

    private DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");


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

    @Test
    public void testReturnOldestPerson() {
        Contact steve = Contact.builder()
                .dateOfBirth(LocalDate.parse("01/02/1987",DATE_TIME_FORMATTER))
                .lastName("Smith")
                .firstName("Steve")
                .gender(Gender.MALE)
                .build() ;

        Contact john  = Contact.builder()
                .dateOfBirth(LocalDate.parse("01/02/1990", DATE_TIME_FORMATTER))
                .lastName("Smith")
                .firstName("John")
                .gender(Gender.MALE)
                .build() ;

        Contact bruce  = Contact.builder()
                .dateOfBirth(LocalDate.parse("01/02/1985", DATE_TIME_FORMATTER))
                .lastName("Smith")
                .firstName("Bruce")
                .gender(Gender.MALE)
                .build() ;

        AddressBook addressBook = AddressBook.builder()
                .contactList(Arrays.asList(steve, john, bruce))
                .build() ;

        assertEquals(bruce ,addressBookService.getOldestPerson(addressBook));

    }

}
