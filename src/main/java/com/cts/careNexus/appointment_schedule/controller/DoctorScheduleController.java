package com.cts.careNexus.appointment_schedule.controller;

import com.cts.careNexus.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.appointment_schedule.service.DoctorScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctor-schedules")
@Validated
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<DoctorSchedule> createSchedule(@Valid @RequestBody DoctorSchedule schedule) {
        try {
            DoctorSchedule created = scheduleService.createSchedule(schedule);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorSchedule>> getAllSchedules() {
        try {
            List<DoctorSchedule> schedules = scheduleService.getAllSchedules();
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorSchedule> getScheduleById(@PathVariable Long id) {
        try {
            return scheduleService.getScheduleById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/doctor/{doctorID}")
    public ResponseEntity<List<DoctorSchedule>> getSchedulesByDoctorId(@PathVariable Long doctorID) {
        try {
            List<DoctorSchedule> schedules = scheduleService.getSchedulesByDoctorId(doctorID);
            if (schedules.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorSchedule> updateSchedule(@PathVariable Long id, @Valid @RequestBody DoctorSchedule scheduleDetails) {
        try {
            return scheduleService.updateSchedule(id, scheduleDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorSchedule> patchSchedule(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            return scheduleService.patchSchedule(id, updates)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSchedule(@PathVariable Long id) {
        try {
            if (scheduleService.deleteSchedule(id)) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Schedule deleted successfully");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}