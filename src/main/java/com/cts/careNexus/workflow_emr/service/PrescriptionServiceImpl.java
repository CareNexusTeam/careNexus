package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import com.cts.careNexus.workflow_emr.dto.PrescriptionDTO;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.workflow_emr.repository.ConsultationRepository;
import com.cts.careNexus.workflow_emr.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl
        implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final ConsultationRepository consultationRepository;

    private final PatientRepo patientRepository;

    public PrescriptionServiceImpl(
            PrescriptionRepository prescriptionRepository,
            ConsultationRepository consultationRepository,
            PatientRepo patientRepository) {

        this.prescriptionRepository = prescriptionRepository;
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public PrescriptionDTO createPrescription(
            PrescriptionDTO dto) {

        Consultation consultation =
                consultationRepository.findById(dto.getConsultationId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Consultation not found with id : "
                                                + dto.getConsultationId()));

        Patient patient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id : "
                                                + dto.getPatientId()));

        Prescription prescription =
                new Prescription();

        prescription.setConsultation(consultation);
        prescription.setPatient(patient);
        prescription.setMedicationName(dto.getMedicationName());
        prescription.setDosage(dto.getDosage());
        prescription.setFrequency(dto.getFrequency());
        prescription.setDuration(dto.getDuration());

        prescription.setStatus(
                Prescription.PrescriptionStatus
                        .valueOf(dto.getStatus()));

        Prescription saved =
                prescriptionRepository.save(prescription);

        return convertToDTO(saved);
    }

    @Override
    public List<PrescriptionDTO> getAllPrescriptions() {

        return prescriptionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PrescriptionDTO> getPrescriptionById(
            Long id) {

        return prescriptionRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionsByPatientId(
            Long patientId) {

        return prescriptionRepository
                .findByPatient_PatientId(patientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionsByConsultationId(
            Long consultationId) {

        return prescriptionRepository
                .findByConsultation_ConsultationID(consultationId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrescriptionDTO updatePrescription(
            Long id,
            PrescriptionDTO dto) {

        Prescription existing =
                prescriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Prescription not found with id : "
                                                + id));

        Consultation consultation =
                consultationRepository.findById(dto.getConsultationId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Consultation not found with id : "
                                                + dto.getConsultationId()));

        Patient patient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id : "
                                                + dto.getPatientId()));

        existing.setConsultation(consultation);
        existing.setPatient(patient);
        existing.setMedicationName(dto.getMedicationName());
        existing.setDosage(dto.getDosage());
        existing.setFrequency(dto.getFrequency());
        existing.setDuration(dto.getDuration());

        existing.setStatus(
                Prescription.PrescriptionStatus
                        .valueOf(dto.getStatus()));

        return convertToDTO(
                prescriptionRepository.save(existing));
    }

    @Override
    public void deletePrescription(Long id) {

        Prescription prescription =
                prescriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Prescription not found with id : "
                                                + id));

        prescriptionRepository.delete(prescription);
    }

    private PrescriptionDTO convertToDTO(
            Prescription prescription) {

        PrescriptionDTO dto =
                new PrescriptionDTO();

        dto.setPrescriptionId(
                prescription.getPrescriptionID());

        dto.setConsultationId(
                prescription.getConsultation()
                        .getConsultationID());

        dto.setPatientId(
                prescription.getPatient()
                        .getPatientId());

        dto.setMedicationName(
                prescription.getMedicationName());

        dto.setDosage(
                prescription.getDosage());

        dto.setFrequency(
                prescription.getFrequency());

        dto.setDuration(
                prescription.getDuration());

        dto.setStatus(
                prescription.getStatus().name());

        return dto;
    }
}