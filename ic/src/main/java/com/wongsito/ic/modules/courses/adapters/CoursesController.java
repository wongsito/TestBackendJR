package com.wongsito.ic.modules.courses.adapters;

import com.wongsito.ic.modules.courses.entities.Courses;
import com.wongsito.ic.modules.courses.use_cases.methods.CoursesRepository;
import com.wongsito.ic.modules.courses.use_cases.services.CoursesService;
import com.wongsito.ic.modules.students.use_cases.methods.StudentsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    private final CoursesRepository coursesRepository;
    private final CoursesService coursesService;

    public CoursesController(CoursesRepository coursesRepository, CoursesService coursesService) {
        this.coursesRepository = coursesRepository;
        this.coursesService = coursesService;
    }

    @GetMapping("/read")
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = coursesRepository.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/read-one/{id}")
    public ResponseEntity<Courses> getCourseById(@PathVariable Long id) {
        Optional<Courses> course = coursesRepository.findById(id.toString());
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Courses> createCourse(@RequestBody Courses course) {
        Courses savedCourse = coursesRepository.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Courses> updateCourse(@PathVariable Long id, @RequestBody Courses courseDetails) {
        Optional<Courses> optionalCourse = coursesRepository.findById(id.toString());

        if (optionalCourse.isPresent()) {
            Courses course = optionalCourse.get();
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            course.setTypeApproval(courseDetails.getTypeApproval());
            Courses updatedCourse = coursesRepository.save(course);
            coursesService.updateRegistrations(course.getId(), course.getTypeApproval());
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Optional<Courses> optionalCourse = coursesRepository.findById(id.toString());

        if (optionalCourse.isPresent()) {
            coursesService.deleteRegistrations(optionalCourse.get().getId());
            coursesRepository.delete(optionalCourse.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
