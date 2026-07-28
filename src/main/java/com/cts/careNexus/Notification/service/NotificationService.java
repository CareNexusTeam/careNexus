package com.cts.careNexus.Notification.service;

import com.cts.careNexus.Notification.DTO.NotificationDTO;
import com.cts.careNexus.Notification.entity.Notification;
import com.cts.careNexus.Notification.entity.Notification.NotificationCategory;
import com.cts.careNexus.Notification.entity.Notification.NotificationStatus;
import com.cts.careNexus.Notification.repository.NotificationRepository;
import com.cts.careNexus.appointment_schedule.entity.Appointment;
import com.cts.careNexus.appointment_schedule.repository.AppointmentRepository;
import com.cts.careNexus.billingandinsurance.entities.Invoice;
import com.cts.careNexus.billingandinsurance.repository.InvoiceRepository;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import com.cts.careNexus.workflow_emr.repository.ConsultationRepository;
import com.cts.careNexus.workflow_emr.repository.PrescriptionRepository;
import com.cts.careNexus.exception.InvalidRequestException;
import com.cts.careNexus.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service managing database-driven user notifications.
 * Automatically catches database records across categories (Appointments, Billing, Pharmacy, Clinical)
 * and generates corresponding user notifications (1 for patient, 1 for doctor on appointments).
 */
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    // Injected JPA repositories for DB data fetching
    @Autowired 
    private NotificationRepository notificationRepo;
    
    @Autowired 
    private UserRepo userRepo;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Convert JPA Notification entity to DTO
    public NotificationDTO convertToDTO(Notification n) {
        if (n == null) return null;
        return NotificationDTO.builder()
                .notificationId(n.getNotificationId())
                .userId(n.getUser() != null ? n.getUser().getUserId() : null)
                .message(n.getMessage())
                .category(n.getCategory() != null ? n.getCategory().name() : null)
                .status(n.getStatus() != null ? n.getStatus().name() : null)
                .createdDate(n.getCreatedDate())
                .build();
    }

    /**
     * Automatically syncs and fetches user notifications by catching DB records across categories:
     * - Appointment Category: Generates 1 notification for doctor and 1 for patient
     * - Billing Category: Generates notifications for patient invoices
     * - Clinical Category: Generates notifications for consultations
     * - Pharmacy Category: Generates notifications for prescriptions
     */
    public List<NotificationDTO> getNotificationsByUser(Long userId) {
        log.info("Fetching and syncing automatic DB notifications for user: {}", userId);
        if (userId == null || userId <= 0) {
            log.warn("Fetching notifications failed: invalid user ID: {}", userId);
            throw new InvalidRequestException("Invalid user ID.");
        }

        User targetUser = userRepo.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Fetching notifications failed: user not found: {}", userId);
                    return new ResourceNotFoundException("User not found with ID: " + userId);
                });

        // 1. Process Appointment DB records: generate 1 notification for Doctor & 1 for Patient
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment appt : appointments) {
            // Patient notification
            if (appt.getPatientID() != null && appt.getPatientID().getPatientId() != null && appt.getPatientID().getPatientId().equals(userId)) {
                String doctorName = appt.getDoctorID() != null ? appt.getDoctorID().getName() : "Doctor";
                String msg = "Appointment #" + appt.getAppointmentID() + " scheduled with Dr. " + doctorName + " on " + appt.getScheduledDateTime();
                createNotificationIfNotExists(targetUser, msg, NotificationCategory.Appointment);
            }
            // Doctor notification
            if (appt.getDoctorID() != null && appt.getDoctorID().getUserId() != null && appt.getDoctorID().getUserId().equals(userId)) {
                String patientName = appt.getPatientID() != null ? appt.getPatientID().getName() : "Patient";
                String msg = "New appointment #" + appt.getAppointmentID() + " scheduled with patient " + patientName + " on " + appt.getScheduledDateTime();
                createNotificationIfNotExists(targetUser, msg, NotificationCategory.Appointment);
            }
        }

        // 2. Process Billing DB records: catch unpaid/pending invoices for patient
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice inv : invoices) {
            if (inv.getPatient() != null && inv.getPatient().getPatientId() != null && inv.getPatient().getPatientId().equals(userId)) {
                String msg = "Invoice #" + inv.getInvoiceID() + " - Status: " + inv.getStatus() + ", Outstanding Amount: $" + inv.getOutstandingAmount();
                createNotificationIfNotExists(targetUser, msg, NotificationCategory.Billing);
            }
        }

        // 3. Process Clinical DB records: catch consultations
        List<Consultation> consultations = consultationRepository.findAll();
        for (Consultation c : consultations) {
            boolean isPatient = c.getPatient() != null && c.getPatient().getPatientId() != null && c.getPatient().getPatientId().equals(userId);
            boolean isDoctor = c.getDoctor() != null && c.getDoctor().getUserId() != null && c.getDoctor().getUserId().equals(userId);
            if (isPatient || isDoctor) {
                String msg = "Consultation #" + c.getConsultationID() + " - Status: " + c.getStatus() + ", Diagnosis: " + (c.getDiagnosis() != null ? c.getDiagnosis() : "Pending");
                createNotificationIfNotExists(targetUser, msg, NotificationCategory.Clinical);
            }
        }

        // 4. Process Pharmacy DB records: catch prescriptions
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        for (Prescription p : prescriptions) {
            if (p.getPatient() != null && p.getPatient().getPatientId() != null && p.getPatient().getPatientId().equals(userId)) {
                String msg = "Prescription #" + p.getPrescriptionID() + " issued for " + p.getMedicationName() + " (" + p.getDosage() + ")";
                createNotificationIfNotExists(targetUser, msg, NotificationCategory.Pharmacy);
            }
        }

        // Return all synchronized DB notifications for user
        List<Notification> userNotifications = notificationRepo.findByUserUserId(userId);
        return userNotifications.stream().map(this::convertToDTO).toList();
    }

    // Helper method to create notification in DB if not already present
    private void createNotificationIfNotExists(User user, String message, NotificationCategory category) {
        if (!notificationRepo.existsByUserUserIdAndMessage(user.getUserId(), message)) {
            Notification n = Notification.builder()
                    .user(user)
                    .message(message)
                    .category(category)
                    .status(NotificationStatus.Unread)
                    .createdDate(LocalDateTime.now())
                    .build();
            notificationRepo.save(n);
            log.info("Auto-generated DB notification for user {}: [{}]", user.getUserId(), category);
        }
    }

    // Mark notification as Read
    public NotificationDTO markAsRead(Long id) {
        log.info("Marking notification {} as read.", id);
        if (id == null || id <= 0) {
            log.warn("Mark as read failed: invalid ID: {}", id);
            throw new InvalidRequestException("Invalid notification ID.");
        }
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Mark as read failed: notification not found: {}", id);
                    return new ResourceNotFoundException("Notification not found with ID: " + id);
                });

        n.setStatus(NotificationStatus.Read);
        Notification saved = notificationRepo.save(n);
        log.info("Notification {} marked as Read.", id);
        return convertToDTO(saved);
    }

    // Mark notification as Dismissed
    public NotificationDTO dismissNotification(Long id) {
        log.info("Dismissing notification {}.", id);
        if (id == null || id <= 0) {
            log.warn("Dismissal failed: invalid ID: {}", id);
            throw new InvalidRequestException("Invalid notification ID.");
        }
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Dismissal failed: notification not found: {}", id);
                    return new ResourceNotFoundException("Notification not found with ID: " + id);
                });

        n.setStatus(NotificationStatus.Dismissed);
        Notification saved = notificationRepo.save(n);
        log.info("Notification {} marked as Dismissed.", id);
        return convertToDTO(saved);
    }

    // Delete notification record
    public void deleteNotification(Long id) {
        log.info("Deleting notification: {}", id);
        if (id == null || id <= 0) {
            log.warn("Deletion failed: invalid ID: {}", id);
            throw new InvalidRequestException("Invalid notification ID.");
        }
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Deletion failed: notification not found: {}", id);
                    return new ResourceNotFoundException("Notification not found with ID: " + id);
                });
        
        notificationRepo.delete(n);
        log.info("Notification {} deleted.", id);
    }

    // Get total count of unread notifications for a user
    public long getUnreadCount(Long userId) {
        log.info("Fetching unread count for user: {}", userId);
        if (userId == null || userId <= 0) {
            log.warn("Unread count retrieval failed: invalid user ID: {}", userId);
            throw new InvalidRequestException("Invalid user ID.");
        }
        userRepo.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Unread count retrieval failed: user not found: {}", userId);
                    return new ResourceNotFoundException("User not found with ID: " + userId);
                });

        return notificationRepo.countByUserUserIdAndStatus(userId, NotificationStatus.Unread);
    }
}
