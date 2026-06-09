package com.cts.careNexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Appointment;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {
}