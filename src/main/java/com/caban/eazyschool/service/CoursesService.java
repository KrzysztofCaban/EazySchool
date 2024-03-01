package com.caban.eazyschool.service;

import com.caban.eazyschool.model.Courses;
import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.repository.CoursesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CoursesService {

    private final CoursesRepository coursesRepository;

    private final PersonService personService;

    public CoursesService(CoursesRepository coursesRepository, PersonService personService) {
        this.coursesRepository = coursesRepository;
        this.personService = personService;
    }

    public List<Courses> findAllCourses() {
        return coursesRepository.findAll();
    }

    @Transactional
    public void saveCourse(Courses course) {
        coursesRepository.save(course);
    }

    public Optional<Courses> findCourseById(int id) {
        return coursesRepository.findById(id);
    }

    @Transactional
    public void addNewStudent(Courses courses, Person personEntity) {
        courses.addStudent(personEntity);
        coursesRepository.save(courses);
    }

    @Transactional
    public void deleteStudentFromCourse(Courses courses, int personId) {
        Person person = personService.findPersonById(personId);

        courses.deleteStudent(person);

        coursesRepository.save(courses);
    }
}
