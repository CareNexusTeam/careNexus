package com.cts.careNexus.moduls.appointment_schedule.repository;

import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorID(Integer doctorID);
}
