package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.example.demo.entity.Appointment;
import com.example.demo.repository.AppointmentRepository;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository repo;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return repo.findAll();
    }
}
