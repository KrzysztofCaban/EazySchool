package com.caban.eazyschool.service;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.Contact;
import com.caban.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact){

        contact.setStatus(String.valueOf(EazySchoolConstants.OPEN));
        contact.setCreatedBy(String.valueOf(EazySchoolConstants.ANONYMOUS));
        contact.setCreatedAt(LocalDateTime.now());
        int result = contactRepository.saveContactMsg(contact);

        return result > 0;
    }

    public List<Contact> findMsgsWithOpenStatus() {
        return contactRepository.findMsgsWithStatus(EazySchoolConstants.OPEN);
    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        int result = contactRepository.updateMsgStatus(contactId, String.valueOf(EazySchoolConstants.CLOSE), updatedBy);

        return result > 0;
    }
}
