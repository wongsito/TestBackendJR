package com.wongsito.ic.modules.courses.use_cases.methods;

import com.wongsito.ic.modules.courses.entities.Courses;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface CoursesRepository extends JpaRepository<Courses, String> {

    @Modifying
    @Query(value = "CALL sp_update_registrations_per_user(?, ?);", nativeQuery = true)
    void updateRegistrations(@Param("id") Long id, @Param("typeApproval") Courses.TypeApproval typeApproval);

    @Modifying
    @Query(value = "CALL sp_delete_registrations_per_course(?);", nativeQuery = true)
    void deleteRegistrations(@Param("id") Long id);
}
