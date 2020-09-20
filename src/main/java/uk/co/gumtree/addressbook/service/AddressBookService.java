package uk.co.gumtree.addressbook.service;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.co.gumtree.addressbook.domain.AddressBook;
import uk.co.gumtree.addressbook.domain.Contact;
import uk.co.gumtree.addressbook.domain.Gender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    private final String SEPARATOR = ",";

    private DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("d/MM/yyyy");

    /**
     * Return number of person based on Gender
     * @param addressBook
     * @param gender
     * @return Long
     */

    public long numberOfPersonWithGender(AddressBook addressBook, Gender gender){
        return addressBook.getContactList().stream().filter(contact -> contact.getGender().equals(gender)).count();
    }

    /**
     * Return oldest person in address book
     * @param addressBook
     * @return Contact
     */
    public Contact getOldestPerson(AddressBook addressBook) {

        Comparator<Contact> ageComparator = (p1, p2) -> p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
        return addressBook.getContactList().stream()
                .min(ageComparator).get();
    }

    /**
     * Return difference between two person in days .
     * @param addressBook
     * @param personName
     * @param otherPersonName
     * @return
     */
    public String howManyDaysOldIsPersonOneFromOtherPerson( AddressBook addressBook, String personName, String otherPersonName) {

        if (personName == null || otherPersonName == null) {
            return null;
        }

        Contact firstPerson = addressBook.getContactList().stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase(personName))
                .findFirst()
                .orElse(null);

        Contact otherPerson = addressBook.getContactList().stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase(otherPersonName))
                .findFirst()
                .orElse(null);

        if (firstPerson == null || otherPerson == null) {
            return null;
        }

        int days = Days.daysBetween(firstPerson.getDateOfBirth(), otherPerson.getDateOfBirth()).getDays();

        return String.valueOf(days);
    }

    public AddressBook readAddressBookFromFile(String filePath) throws IOException {
        AddressBook addressBook  = AddressBook.builder()
                .build();

        if (filePath == null) {
            return addressBook;
        }

        InputStream pathResource = new ClassPathResource(filePath)
                .getInputStream();
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(pathResource));

        List<String> collect = bufferedReader.lines().collect(Collectors.toList());

        collect.stream().map(this::parsePerson)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return addressBook;
    }

    public Contact parsePerson(String s) {

        String[] personDetail = s.split(SEPARATOR);

        if (personDetail == null || personDetail .length < 3 ) {
            return null;
        }

        return Contact.builder()
                .firstName(getFirstName(personDetail[0]))
                .lastName(getLastName(personDetail[0]))
                .dateOfBirth(parseDateOfBirth(personDetail[1]))
                .gender(parseGender(personDetail[2]))
                .build();
    }

    public Gender parseGender(String gender) {

        if (gender == null){
            return null;
        }
        return Gender.valueOf(gender.toUpperCase());
    }

    public LocalDate parseDateOfBirth(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }

    public String getLastName(String name) {

        String[] fullName = parseName(name);
        if (fullName == null || fullName.length < 2) return null;
        return fullName[1];
    }

    public String getFirstName(String name) {

        String[] fullName = parseName(name);
        if (fullName == null || fullName.length < 1) return null;
        return fullName[0];
    }


    public String[] parseName(String name) {
        if (name == null) {
            return null;
        }
        String[] fullName = name.split(" ");
        return fullName;
    }
}
