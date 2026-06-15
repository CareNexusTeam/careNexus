package com.cts.careNexus.moduls.appointment_schedule.workflow_emr.repository;

import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
