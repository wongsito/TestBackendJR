package com.wongsito.ic.modules.students.adapters;

import com.wongsito.ic.modules.registrations.use_cases.methods.RegistrationsRepository;
import com.wongsito.ic.modules.registrations.use_cases.services.RegistrationsService;
import com.wongsito.ic.modules.students.entities.Students;
import com.wongsito.ic.modules.students.use_cases.methods.StudentsRepository;
import com.wongsito.ic.modules.students.use_cases.services.StudentsService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private final StudentsRepository studentsRepository;
    private final RegistrationsRepository registrationsRepository;
    private final RegistrationsService registrationsService;
    private final StudentsService studentsService;

    public StudentsController(StudentsRepository studentsRepository, RegistrationsRepository registrationsRepository, RegistrationsService registrationsService, StudentsService studentsService) {
        this.studentsRepository = studentsRepository;
        this.registrationsRepository = registrationsRepository;
        this.registrationsService = registrationsService;
        this.studentsService = studentsService;
    }

    @PostConstruct
    public void initRoleAndUser() throws ParseException {
        studentsService.initRoleAndUser();
    }

    @GetMapping("/read")
    public ResponseEntity<List> getAllStudents() {
        List<Students> students = studentsRepository.findAll();

        if(!students.isEmpty()){
            return new ResponseEntity<>(students, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(students, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-one/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable Long id) {
        return studentsRepository.findById(id.toString())
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Students> createStudent(@RequestBody Students student) {
        Students savedStudent = studentsRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Students> updateStudent(@PathVariable Long id, @RequestBody Students studentDetails) {
        return studentsRepository.findById(id.toString())
                .map(student -> {
                    student.setName(studentDetails.getName());
                    student.setEmail(studentDetails.getEmail());
                    Students updatedStudent = studentsRepository.save(student);
                    return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Optional<Students> optionalStudent = studentsRepository.findById(id.toString());
        if (optionalStudent.isPresent()) {
            Students student = optionalStudent.get();
            studentsService.deleteRegPerStudent(student.getId());
            studentsRepository.delete(student);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
