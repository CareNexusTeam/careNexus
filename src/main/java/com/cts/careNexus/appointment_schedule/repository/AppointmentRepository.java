package com.cts.careNexus.appointment_schedule.repository;

import com.cts.careNexus.appointment_schedule.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}