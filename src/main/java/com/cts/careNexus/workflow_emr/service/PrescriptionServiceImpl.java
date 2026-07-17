package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import com.cts.careNexus.workflow_emr.dto.PrescriptionRequestDTO;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import com.cts.careNexus.workflow_emr.repository.ConsultationRepository;
import com.cts.careNexus.workflow_emr.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Override
    public Prescription createPrescription(PrescriptionRequestDTO dto) {

        Prescription prescription = new Prescription();

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation not found"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        prescription.setConsultation(consultation);
        prescription.setPatient(patient);
        prescription.setMedicationName(dto.getMedicationName());
        prescription.setDosage(dto.getDosage());


        prescription.setFrequency(dto.getFrequency());
        prescription.setDuration(dto.getDuration());

        prescription.setStatus(Prescription.PrescriptionStatus.valueOf(dto.getStatus()));

        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatient_PatientId(patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByConsultationId(Long consultationId) {
        return prescriptionRepository.findByConsultation_ConsultationID(consultationId);
    }

    @Override
    public Optional<Prescription> updatePrescription(Long id, PrescriptionRequestDTO dto) {

        return prescriptionRepository.findById(id).map(existing -> {

            Consultation consultation = consultationRepository.findById(dto.getConsultationId()).orElseThrow();
            Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow();

            existing.setConsultation(consultation);
            existing.setPatient(patient);
            existing.setMedicationName(dto.getMedicationName());
            existing.setDosage(dto.getDosage());


            existing.setFrequency(dto.getFrequency());
            existing.setDuration(dto.getDuration());

            existing.setStatus(Prescription.PrescriptionStatus.valueOf(dto.getStatus()));

            return prescriptionRepository.save(existing);
        });
    }

    @Override
    public boolean deletePrescription(Long id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
