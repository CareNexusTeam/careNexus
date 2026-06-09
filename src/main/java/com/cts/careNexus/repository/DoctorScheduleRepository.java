package com.cts.careNexus.repository;

import com.cts.careNexus.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    // Optional: find schedules by doctor
    List<DoctorSchedule> findByDoctorID(Integer doctorID);
}
