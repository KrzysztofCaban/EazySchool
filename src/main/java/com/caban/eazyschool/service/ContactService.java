package com.caban.eazyschool.service;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.Contact;
import com.caban.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact) {

        contact.setStatus(EazySchoolConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);

        return savedContact.getContactId() > 0;
    }

    public List<Contact> findMsgsWithOpenStatus() {
        return contactRepository.findByStatus(EazySchoolConstants.OPEN);
    }

    @Transactional
    public boolean updateMsgStatus(int contactId) {
        Optional<Contact> contact = contactRepository.findById(contactId);

        contact.ifPresent(contact1 ->
                contact1.setStatus(EazySchoolConstants.CLOSE)
        );

        Contact savedContact = contactRepository.save(contact.get());
        return savedContact.getContactId() > 0;
    }
}
