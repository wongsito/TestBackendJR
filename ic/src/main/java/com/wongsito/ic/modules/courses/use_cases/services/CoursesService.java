package com.wongsito.ic.modules.courses.use_cases.services;

import com.wongsito.ic.modules.courses.entities.Courses;
import com.wongsito.ic.modules.courses.use_cases.methods.CoursesRepository;
import com.wongsito.ic.modules.courses.use_cases.repositories.ICoursesRepository;
import org.springframework.stereotype.Service;

@Service
public class CoursesService implements ICoursesRepository {

    private final CoursesRepository coursesRepository;

    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Override
    public void updateRegistrations(Long id, Courses.TypeApproval typeApproval) {
        coursesRepository.updateRegistrations(id, typeApproval);
    }

    @Override
    public void deleteRegistrations(Long id) {
        coursesRepository.deleteRegistrations(id);
    }


}
