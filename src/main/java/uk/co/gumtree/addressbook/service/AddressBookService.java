package uk.co.gumtree.addressbook.service;

import org.springframework.stereotype.Service;
import uk.co.gumtree.addressbook.domain.AddressBook;
import uk.co.gumtree.addressbook.domain.Contact;
import uk.co.gumtree.addressbook.domain.Gender;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Service
public class AddressBookService {

    private DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");

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
}
