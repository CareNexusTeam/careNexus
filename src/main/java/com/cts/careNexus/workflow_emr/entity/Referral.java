package com.cts.careNexus.workflow_emr.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Referral")
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReferralID")
    private Long referralID;

    @Column(name = "ConsultationID")
    private Integer consultationID;

    @Column(name = "ReferredToDepartment")
    private String referredToDepartment;

    @Column(name = "Reason", columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "Priority")
    private Priority priority;

    @Column(name = "Status")
    private String status;

    // ENUM
    public enum Priority {
        Routine,
        Urgent,
        Emergency
    }
}