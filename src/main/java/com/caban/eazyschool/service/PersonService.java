package com.caban.eazyschool.service;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.model.Roles;
import com.caban.eazyschool.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Person savePerson(Person person) {
        Roles role = rolesService.findRoleByName(EazySchoolConstants.STUDENT_ROLE);

        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        return personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        System.out.println("SERVICE: " + person);
        return personRepository.save(person);
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
