package com.caban.eazyschool.service;

import com.caban.eazyschool.model.Contact;
import com.caban.eazyschool.model.Courses;
import com.caban.eazyschool.model.EazyClass;
import com.caban.eazyschool.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final ContactService contactService;
    private final EazyClassService eazyClassService;
    private final PersonService personService;
    private final CoursesService coursesService;


    public AdminService(ContactService contactService,
                        EazyClassService eazyClassService,
                        PersonService personService,
                        CoursesService coursesService) {
        this.contactService = contactService;
        this.eazyClassService = eazyClassService;
        this.personService = personService;
        this.coursesService = coursesService;
    }

    public Page<Contact> findMsgsWithOpenStatus(PageRequest pageRequest) {
        return contactService.findMsgsWithOpenStatus(pageRequest);
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
            return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId() + "&error=true";

        personService.signUpForClass(personEntity, eazyClass);
        eazyClassService.addNewStudent(eazyClass, personEntity);

        return "redirect:/admin/displayStudents?classId=" + eazyClass.getClassId();
    }

    public List<EazyClass> getAllClasses() {
        return eazyClassService.findAllClasses();
    }

    public void deleteStudentFromClass(EazyClass eazyClass, int personId) {
        personService.leaveClass(personId);
        eazyClassService.deleteStudentFromClass(eazyClass, personId);
    }

    public List<Courses> findAllCourses() {
        return coursesService.findAllCourses();
    }

    public void addNewCourse(Courses course) {
        coursesService.saveCourse(course);
    }

    public Optional<Courses> getCourseById(int id) {
        return coursesService.findCourseById(id);
    }

    public String addNewStudentToCourse(Courses courses, Person person) {
        Person personEntity = personService.findPersonByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0))
            return "redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true";

        personService.signUpForCourse(personEntity, courses);
        coursesService.addNewStudent(courses, personEntity);
        return "redirect:/admin/viewStudents?id=" + courses.getCourseId();
    }


    public void deleteStudentFromCourse(Courses courses, int personId) {
        personService.leaveCourse(personId, courses);
        coursesService.deleteStudentFromCourse(courses, personId);
    }
}
