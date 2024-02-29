package com.caban.eazyschool.service;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.model.Roles;
import com.caban.eazyschool.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final RolesService rolesService;

    public PersonService(PersonRepository personRepository, RolesService rolesService) {
        this.personRepository = personRepository;
        this.rolesService = rolesService;
    }

    public boolean savePerson(Person person) {
        Roles role = rolesService.findRoleByName(EazySchoolConstants.STUDENT_ROLE);

        person.setRoles(role);

        person = personRepository.save(person);

        return person.getPersonId() > 0;
    }
}
