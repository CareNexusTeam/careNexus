package com.carenexus.carenexus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "AuditLog")
@Data                   // Isse Getters, Setters, toString(), equals(), aur hashCode() apne aap ban jayenge
@NoArgsConstructor      // Default constructor ke liye
@AllArgsConstructor     // Parameterized constructor ke liye
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditID")
    private int auditId; // Unique log entry number [cite: 30]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false)
    private User user; // The ID of the person who made the change [cite: 30]

    @Column(name = "Action", length = 255)
    private String action; // What the user did [cite: 30]

    @Column(name = "RecordType", length = 100)
    private String recordType; // The name of the database table that was modified [cite: 30]

    @Column(name = "Timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now(); // Exact date and time [cite: 30]
}