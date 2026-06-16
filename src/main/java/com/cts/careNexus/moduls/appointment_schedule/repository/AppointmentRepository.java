package com.cts.careNexus.moduls.appointment_schedule.repository;

import com.cts.careNexus.moduls.appointment_schedule.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}