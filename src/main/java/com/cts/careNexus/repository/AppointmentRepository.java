package com.cts.careNexus.repository;

// CRITICAL: Ensure this is the exact package being imported!
import com.cts.careNexus.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Generics must be <Appointment, Long> exactly matching your entity's ID type
}