package uk.co.gumtree.addressbook.service;

import org.springframework.stereotype.Service;
import uk.co.gumtree.addressbook.domain.AddressBook;
import uk.co.gumtree.addressbook.domain.Gender;

@Service
public class AddressBookService {

    /**
     * Return number of person based on Gender
     * @param addressBook
     * @param gender
     * @return
     */

    public long numberOfPersonWithGender(AddressBook addressBook, Gender gender){
        return addressBook.getContactList().stream().filter(contact -> contact.getGender().equals(gender)).count();
    }

}
