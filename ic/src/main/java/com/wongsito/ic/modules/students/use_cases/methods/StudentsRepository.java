package com.wongsito.ic.modules.students.use_cases.methods;

import com.wongsito.ic.modules.students.entities.Students;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface StudentsRepository extends JpaRepository<Students, String> {

    @Modifying
    @Query(value = "CALL sp_delete_registrations_per_user(?);", nativeQuery = true)
    void deleteRegPerStudent(@Param("id") Long id);

}
