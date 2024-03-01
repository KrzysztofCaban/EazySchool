package com.caban.eazyschool.service;

import com.caban.eazyschool.model.EazyClass;
import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.repository.EazyClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EazyClassService {

    private final EazyClassRepository eazyClassRepository;

    private final PersonService personService;

    public EazyClassService(EazyClassRepository eazyClassRepository, PersonService personService) {
        this.eazyClassRepository = eazyClassRepository;
        this.personService = personService;
    }

    public Optional<EazyClass> findClassById(int id) {
        return eazyClassRepository.findById(id);
    }

    @Transactional
    public void saveClass(EazyClass eazyClass) {
        System.out.println("Hallo " + eazyClass);
        eazyClassRepository.save(eazyClass);
    }

    @Transactional
    public void deleteClassById(int id) {
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        for (Person person : eazyClass.get().getPersons()) {
            personService.leaveClass(person.getPersonId());
        }
        eazyClassRepository.deleteById(id);
    }


    @Transactional
    public void addNewStudent(EazyClass eazyClass, Person person) {
        eazyClass.addStudent(person);
        eazyClassRepository.save(eazyClass);
    }

    public List<EazyClass> findAllClasses() {
        return eazyClassRepository.findAll();
    }

    @Transactional
    public EazyClass deleteStudentFromClass(EazyClass eazyClass, int personId) {
        Person person = personService.findPersonById(personId);

        eazyClass.deleteStudent(person);

        return eazyClassRepository.save(eazyClass);
    }
}
