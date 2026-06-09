package com.cts.careNexus.controller;

import com.cts.careNexus.entity.DoctorSchedule;
import com.cts.careNexus.repository.DoctorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @PostMapping
    public ResponseEntity<DoctorSchedule> createSchedule(@RequestBody DoctorSchedule schedule) {
        DoctorSchedule saved = scheduleRepository.save(schedule);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorSchedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorSchedule> getScheduleById(@PathVariable Long id) {
        return scheduleRepository.findById(id)
                .map(schedule -> ResponseEntity.ok(schedule))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/{doctorID}")
    public ResponseEntity<List<DoctorSchedule>> getByDoctor(@PathVariable Integer doctorID) {
        return ResponseEntity.ok(scheduleRepository.findByDoctorID(doctorID));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorSchedule> updateSchedule(
            @PathVariable Long id,
            @RequestBody DoctorSchedule newData) {

        return scheduleRepository.findById(id)
                .map(existing -> {

                    existing.setDoctorID(newData.getDoctorID());
                    existing.setDate(newData.getDate());
                    existing.setStartTime(newData.getStartTime());
                    existing.setEndTime(newData.getEndTime());
                    existing.setSlotDurationMinutes(newData.getSlotDurationMinutes());
                    existing.setAvailableSlots(newData.getAvailableSlots());

                    return new ResponseEntity<>(scheduleRepository.save(existing), HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
