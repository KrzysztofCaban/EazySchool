package com.caban.eazyschool.service;

import com.caban.eazyschool.model.Address;
import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.model.Profile;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final PersonService personService;

    public ProfileService(PersonService personService) {
        this.personService = personService;
    }

    public Profile populateProfileData(Person person) {
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        if (person.getAddress() != null) {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        return profile;
    }

    public Person updateProfile(Person person, Profile profile) {
        System.out.println("PRZED: " +person);

        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress() ==null || !(person.getAddress().getAddressId()>0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

        System.out.println("PO: " +person);

        return personService.updatePerson(person);
    }
}
