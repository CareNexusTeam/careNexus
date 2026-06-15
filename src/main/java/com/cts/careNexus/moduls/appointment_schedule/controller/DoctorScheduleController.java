package com.cts.careNexus.moduls.appointment_schedule.controller;

import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.moduls.appointment_schedule.service.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<DoctorSchedule> createSchedule(@RequestBody DoctorSchedule schedule) {
        DoctorSchedule saved = scheduleService.createSchedule(schedule);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorSchedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorSchedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/{doctorID}")
    public ResponseEntity<List<DoctorSchedule>> getByDoctor(@PathVariable Integer doctorID) {
        return ResponseEntity.ok(scheduleService.getSchedulesByDoctorId(doctorID));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorSchedule> updateSchedule(@PathVariable Long id, @RequestBody DoctorSchedule newData) {
        return scheduleService.updateSchedule(id, newData)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (scheduleService.deleteSchedule(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}