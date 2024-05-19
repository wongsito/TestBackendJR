package com.wongsito.ic.modules.courses.use_cases.repositories;

import com.wongsito.ic.modules.courses.entities.Courses;

public interface ICoursesRepository {

    void updateRegistrations(Long id, Courses.TypeApproval typeApproval);

    void deleteRegistrations(Long id);

}
