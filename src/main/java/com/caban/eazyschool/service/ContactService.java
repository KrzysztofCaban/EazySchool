package com.caban.eazyschool.service;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.Contact;
import com.caban.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        if (contact.getContactId() == 0)
            contact.setStatus(EazySchoolConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);

        return savedContact.getContactId() > 0;
    }

    public Page<Contact> findMsgsWithOpenStatus(PageRequest pageRequest) {
        return contactRepository.findByStatus(EazySchoolConstants.OPEN, pageRequest);
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

    public List<Contact> findContactsByStatus(String status) {
        return contactRepository.findByStatus(status);
    }

    public void deleteMessageById(int id) {
        contactRepository.deleteById(id);
    }

    public Optional<Contact> findContactById(int contactId) {
        return contactRepository.findById(contactId);
    }

    public void deleteMessage(Contact contact) {
        contactRepository.delete(contact);
    }
}
