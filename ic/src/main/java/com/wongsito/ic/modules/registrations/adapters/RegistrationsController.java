package com.wongsito.ic.modules.registrations.adapters;

import com.wongsito.ic.modules.courses.entities.Courses;
import com.wongsito.ic.modules.courses.use_cases.methods.CoursesRepository;
import com.wongsito.ic.modules.registrations.entities.Registrations;
import com.wongsito.ic.modules.registrations.use_cases.dtos.RegistrationDTOImpl;
import com.wongsito.ic.modules.registrations.use_cases.dtos.RegistrationsDTO;
import com.wongsito.ic.modules.registrations.use_cases.methods.RegistrationsRepository;
import com.wongsito.ic.modules.students.entities.Students;
import com.wongsito.ic.modules.students.use_cases.methods.StudentsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registrations")
public class RegistrationsController {

    private final RegistrationsRepository registrationsRepository;

    private final CoursesRepository coursesRepository;

    private final StudentsRepository studentsRepository;


    public RegistrationsController(RegistrationsRepository registrationsRepository, CoursesRepository coursesRepository, StudentsRepository studentsRepository) {
        this.registrationsRepository = registrationsRepository;
        this.coursesRepository = coursesRepository;
        this.studentsRepository = studentsRepository;
    }

    @GetMapping("/read")
    public ResponseEntity<List<Registrations>> getAllRegistrations() {
        List<Registrations> registrations = registrationsRepository.findAll();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @GetMapping("/read-one/{id}")
    public ResponseEntity<Registrations> getRegistrationById(@PathVariable Long id) {
        Optional<Registrations> registration = registrationsRepository.findById(id.toString());
        return registration.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Registrations> createRegistration(@RequestBody RegistrationDTOImpl registrationsDTO) {

        Courses course = coursesRepository.findById(String.valueOf(registrationsDTO.getCourseId()))
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Students student = studentsRepository.findById(String.valueOf(registrationsDTO.getStudentId()))
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Registrations registration = new Registrations();

        registration.setCourses(course);
        registration.setStudent(student);

        boolean isApproved = calculateApproval(course, registrationsDTO.getAssists(), registrationsDTO.getQualification());
        registration.setAssists(registrationsDTO.getAssists());
        registration.setQualification(registrationsDTO.getQualification());
        registration.setApproved(isApproved);

        Registrations savedRegistration = registrationsRepository.save(registration);
        return new ResponseEntity<>(savedRegistration, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Registrations> updateRegistration(@PathVariable Long id, @RequestBody RegistrationDTOImpl registrationDetails) {
        Optional<Registrations> optionalRegistration = registrationsRepository.findById(id.toString());

        if (optionalRegistration.isPresent()) {
            Registrations registration = optionalRegistration.get();
            Courses course = coursesRepository.findById(String.valueOf(registrationDetails.getCourseId()))
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            Students student = studentsRepository.findById(String.valueOf(registrationDetails.getStudentId()))
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            registration.setCourses(course);
            registration.setStudent(student);
            registration.setAssists(registrationDetails.getAssists());
            registration.setQualification(registrationDetails.getQualification());
            boolean isApproved = calculateApproval(course, registrationDetails.getAssists(), registrationDetails.getQualification());
            registration.setApproved(isApproved);
            Registrations updatedRegistration = registrationsRepository.save(registration);
            return new ResponseEntity<>(updatedRegistration, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        Optional<Registrations> optionalRegistration = registrationsRepository.findById(id.toString());

        if (optionalRegistration.isPresent()) {
            registrationsRepository.delete(optionalRegistration.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean calculateApproval(Courses course, Integer assists, Float qualification) {
        switch (course.getTypeApproval()) {
            case assists:
                return assists != null && assists >= 80;
            case qualification:
                return qualification != null && qualification >= 7;
            case mixed:
                return assists != null && assists >= 80 && qualification != null && qualification >= 7;
            default:
                return false;
        }
    }
}
