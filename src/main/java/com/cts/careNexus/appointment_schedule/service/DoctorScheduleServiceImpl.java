package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.appointment_schedule.repository.DoctorScheduleRepository;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public DoctorSchedule createSchedule(DoctorSchedule schedule) {

        if (schedule.getDoctorID() != null &&
                schedule.getDoctorID().getUserId() != null) {

            User doctor = userRepository
                    .findById(schedule.getDoctorID().getUserId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            schedule.setDoctorID(doctor);
        }

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<DoctorSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public Optional<DoctorSchedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<DoctorSchedule> getSchedulesByDoctorId(Long doctorId) {
        return scheduleRepository.findByDoctorID_UserId(doctorId);
    }

    @Override
    public Optional<DoctorSchedule> updateSchedule(Long id, DoctorSchedule newData) {
        return scheduleRepository.findById(id).map(existing -> {

            if (newData.getDoctorID() != null &&
                    newData.getDoctorID().getUserId() != null) {

                User doctor = userRepository
                        .findById(newData.getDoctorID().getUserId())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));

                existing.setDoctorID(doctor);
            }

            existing.setDate(newData.getDate());
            existing.setStartTime(newData.getStartTime());
            existing.setEndTime(newData.getEndTime());
            existing.setSlotDurationMinutes(newData.getSlotDurationMinutes());
            existing.setAvailableSlots(newData.getAvailableSlots());

            return scheduleRepository.save(existing);
        });
    }

    @Override
    public Optional<DoctorSchedule> patchSchedule(Long id, Map<String, Object> updates) {

        return scheduleRepository.findById(id).map(existing -> {

            if (updates.containsKey("doctorID")) {

                Map<String, Object> doctorMap =
                        (Map<String, Object>) updates.get("doctorID");

                if (doctorMap.get("userId") != null) {

                    Long userId =
                            Long.valueOf(doctorMap.get("userId").toString());

                    User doctor = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("Doctor not found"));

                    existing.setDoctorID(doctor);
                }
            }

            if (updates.containsKey("date")) {
                existing.setDate(LocalDate.parse((String) updates.get("date")));
            }

            if (updates.containsKey("startTime")) {
                existing.setStartTime(LocalTime.parse((String) updates.get("startTime")));
            }

            if (updates.containsKey("endTime")) {
                existing.setEndTime(LocalTime.parse((String) updates.get("endTime")));
            }

            if (updates.containsKey("slotDurationMinutes")) {
                existing.setSlotDurationMinutes(
                        Integer.valueOf(updates.get("slotDurationMinutes").toString()));
            }

            if (updates.containsKey("availableSlots")) {
                existing.setAvailableSlots(
                        Integer.valueOf(updates.get("availableSlots").toString()));
            }

            return scheduleRepository.save(existing);
        });
    }

    @Override
    public boolean deleteSchedule(Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}