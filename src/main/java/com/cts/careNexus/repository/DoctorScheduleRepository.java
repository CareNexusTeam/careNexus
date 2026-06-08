package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.DoctorSchedule;

public interface DoctorScheduleRepository
        extends JpaRepository<DoctorSchedule, Long>{
}
