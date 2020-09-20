package uk.co.gumtree.addressbook.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AddressBook {

    @Builder.Default
    private List<Contact> contactList = new ArrayList<>();
}
