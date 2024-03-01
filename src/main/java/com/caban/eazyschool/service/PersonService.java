package com.caban.eazyschool.service;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.EazyClass;
import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.model.Roles;
import com.caban.eazyschool.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final RolesService rolesService;

    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, RolesService rolesService, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.rolesService = rolesService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Person savePerson(Person person) {
        Roles role = rolesService.findRoleByName(EazySchoolConstants.STUDENT_ROLE);

        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        return personRepository.save(person);
    }

    @Transactional
    public Person updatePerson(Person person) {
        System.out.println("SERVICE: " + person);
        return personRepository.save(person);
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }
    @Transactional
    public void leaveClass(int id) {
        Person person = personRepository.findById(id).get();
        person.leaveClass();
        personRepository.save(person);
    }
    @Transactional
    public void signUpForClass(Person person, EazyClass eazyClass) {
        person.signUpForClass(eazyClass);
        personRepository.save(person);
    }

    public Person findPersonById(int id) {
        return personRepository.findById(id).get();
    }
}
