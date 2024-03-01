package com.caban.eazyschool.service;

import com.caban.eazyschool.model.Contact;
import com.caban.eazyschool.model.EazyClass;
import com.caban.eazyschool.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final ContactService contactService;
    private final EazyClassService eazyClassService;
    private final PersonService personService;

    public AdminService(ContactService contactService, EazyClassService eazyClassService, PersonService personService) {
        this.contactService = contactService;
        this.eazyClassService = eazyClassService;
        this.personService = personService;
    }

    public List<Contact> findMsgsWithOpenStatus() {
        return contactService.findMsgsWithOpenStatus();
    }

    public void updateMsgStatus(int contactId) {
        contactService.updateMsgStatus(contactId);
    }

    public void addNewClass(EazyClass eazyClass) {
        eazyClassService.saveClass(eazyClass);
    }

    public void deleteClassById(int id) {
        eazyClassService.deleteClassById(id);

    }

    public Optional<EazyClass> getClassById(int classId) {
        return eazyClassService.findClassById(classId);
    }


    public String addNewStudentToClass(EazyClass eazyClass, Person person) {
        Person personEntity = personService.findPersonByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0))
            return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId()
                    + "&error=true";

        personService.signUpForClass(personEntity, eazyClass);
        eazyClassService.addNewStudent(eazyClass, personEntity);

        return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId();
    }

    public List<EazyClass> getAllClasses() {
        return eazyClassService.findAllClasses();
    }

    public EazyClass deleteStudentFromClass(EazyClass eazyClass, int personId) {
        personService.leaveClass(personId);
        EazyClass eazyClassSaved = eazyClassService.deleteStudentFromClass(eazyClass, personId);

        return eazyClassSaved;
    }
}
