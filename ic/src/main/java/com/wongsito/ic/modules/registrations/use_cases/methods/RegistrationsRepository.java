package com.wongsito.ic.modules.registrations.use_cases.methods;

import com.wongsito.ic.modules.registrations.entities.Registrations;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface RegistrationsRepository extends JpaRepository<Registrations, String> {
}
