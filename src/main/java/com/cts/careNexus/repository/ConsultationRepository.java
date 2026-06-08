package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Consultation;

public interface ConsultationRepository
        extends JpaRepository<Consultation, Long> {
}
