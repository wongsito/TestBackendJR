package com.wongsito.ic.modules.students.use_cases.services;

import com.wongsito.ic.modules.courses.entities.Courses;
import com.wongsito.ic.modules.courses.use_cases.methods.CoursesRepository;
import com.wongsito.ic.modules.registrations.entities.Registrations;
import com.wongsito.ic.modules.registrations.use_cases.methods.RegistrationsRepository;
import com.wongsito.ic.modules.students.entities.Students;
import com.wongsito.ic.modules.students.use_cases.methods.StudentsRepository;
import com.wongsito.ic.modules.students.use_cases.repositories.IStudentsRepository;
import org.springframework.stereotype.Service;
import java.text.ParseException;

@Service
public class StudentsService implements IStudentsRepository {

    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;
    private final RegistrationsRepository registrationsRepository;

    public StudentsService(StudentsRepository studentsRepository, CoursesRepository coursesRepository, RegistrationsRepository registrationsRepository) {
        this.studentsRepository = studentsRepository;
        this.coursesRepository = coursesRepository;
        this.registrationsRepository = registrationsRepository;
    }

    @Override
    public void deleteRegPerStudent(Long id){
        studentsRepository.deleteRegPerStudent(id);
    }

    public void initRoleAndUser() throws ParseException {

        Students student1 = new Students(1L, "Juan Perez", "juan.perez@example.com");
        Students student2 = new Students(2L, "Maria Lopez", "maria.lopez@example.com");
        Students student3 = new Students(3L, "Carlos Martinez", "carlos.martinez@example.com");
        studentsRepository.save(student1);
        studentsRepository.save(student2);
        studentsRepository.save(student3);

        Courses courses1 = new Courses(1L, "Matemáticas", "Curso introductorio de matemáticas básicas", Courses.TypeApproval.mixed);
        Courses courses2 = new Courses(2L, "Programación Java", "Curso avanzado de programación en Java", Courses.TypeApproval.qualification);
        Courses courses3 = new Courses(3L, "Inglés Intermedio", "Curso de inglés intermedio para hispanohablantes", Courses.TypeApproval.assists);
        coursesRepository.save(courses1);
        coursesRepository.save(courses2);
        coursesRepository.save(courses3);

        Registrations registrations1 = new Registrations(1L, courses1, student1, 90, 8.5f, true);
        Registrations registrations2 = new Registrations(2L, courses2, student2, 80, 7.8f, true);
        Registrations registrations3 = new Registrations(3L, courses3, student3, 85, 7.2f, true);
        registrationsRepository.save(registrations1);
        registrationsRepository.save(registrations2);
        registrationsRepository.save(registrations3);
    }
}
