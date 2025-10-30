package com.reuven.healthcare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents an appointment between a patient and a doctor.
 * Each appointment has a defined start and end time.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    // -------------------- Fields --------------------
	@Id // Primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    /**
     * Many appointments can belong to one doctor.
     * This creates a foreign key column 'doctor_id' in the appointments table.
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    /** 
     * Many appointments can belong to one patient.
     * This creates a foreign key column 'patient_id' in the appointments table.
     */
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /**
     * Start time of the appointment.
     * Example: 2025-10-24T09:00
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * End time of the appointment.
     * Example: 2025-10-24T09:30
     */
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    /** Reason for the appointment (e.g., "Check-up", "Follow-up", etc.). */
    private String reason;

    /** Additional notes recorded by the doctor or system. */
    private String notes;

    // -------------------- Constructors --------------------
    public Appointment() { }

    public Appointment(Doctor doctor, Patient patient,
                       LocalDateTime startTime, LocalDateTime endTime,
                       String reason, String notes) {
        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.notes = notes;
    }

    // -------------------- Getters & Setters --------------------
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public Doctor getDoctor() {
    	return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
    	this.doctor = doctor;
    }

    public Patient getPatient() {
    	return patient;
    }
    
    public void setPatient(Patient patient) {
    	this.patient = patient;
    }

    public LocalDateTime getStartTime() {
    	return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) { 
    	this.startTime = startTime;
    }

    public LocalDateTime getEndTime() { 
    	return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) { 
    	this.endTime = endTime;
    }

    public String getReason() { 
    	return reason;
    }
    
    public void setReason(String reason) { 
    	this.reason = reason;
    }

    public String getNotes() { 
    	return notes; 
    }
    
    public void setNotes(String notes) { 
    	this.notes = notes; 
    }
}
